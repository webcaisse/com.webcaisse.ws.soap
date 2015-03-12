package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.ICommandeDao;
import com.webcaisse.dao.hibernate.IProductDao;
import com.webcaisse.dao.hibernate.ISessionDao;
import com.webcaisse.dao.hibernate.ISocieteDao;
import com.webcaisse.dao.hibernate.model.Client;
import com.webcaisse.dao.hibernate.model.Commande;
import com.webcaisse.dao.hibernate.model.Famille;
import com.webcaisse.dao.hibernate.model.LigneCommande;
import com.webcaisse.dao.hibernate.model.Prix;
import com.webcaisse.dao.hibernate.model.Produit;
import com.webcaisse.dao.hibernate.model.Session;
import com.webcaisse.dao.hibernate.model.Societe;
import com.webcaisse.ws.interfaces.CaisseManagerService;
import com.webcaisse.ws.model.ClientIn;
import com.webcaisse.ws.model.CommandeIn;
import com.webcaisse.ws.model.DetailsModePaiementIn;
import com.webcaisse.ws.model.DetailsModePaiementOut;
import com.webcaisse.ws.model.FamilleIn;
import com.webcaisse.ws.model.FamilleOut;
import com.webcaisse.ws.model.LigneCommandeIn;
import com.webcaisse.ws.model.PrixOut;
import com.webcaisse.ws.model.ProduitIn;
import com.webcaisse.ws.model.ProduitOut;

public class CaisseManagerServiceImpl implements CaisseManagerService {

	@Autowired
	IProductDao productDao;
	
	@Autowired 
	ISocieteDao societeDao;
	
	@Autowired 
	ISessionDao sessionDao;

	@Autowired 
	ICommandeDao commandeDao;
	
	private static final String LIBELLE_ESPECE = "Espèce";
	private static final String LIBELLE_CF = "Carte fidelité";
	private static final String LIBELLE_TR = "Ticket restaurent";
	private static final String LIBELLE_CB = "Carte bancaire";
	private static final String LIBELLE_CHEQUE = "Chèque";
	
	public List<com.webcaisse.ws.model.FamilleOut> getFamillesActivees(Long idSociete) {
		List<com.webcaisse.ws.model.FamilleOut> famillesVo = new ArrayList<com.webcaisse.ws.model.FamilleOut>();
		List<Famille> familles = productDao.getFamillies(idSociete);
		for (Famille famille : familles) {
			com.webcaisse.ws.model.FamilleOut fam = new com.webcaisse.ws.model.FamilleOut();
			fam.setLibelle(famille.getLibelle());
			fam.setId(famille.getId());
			fam.setCouleur(famille.getCouleur());
			famillesVo.add(fam);
		}
		return famillesVo;
	}

	public List<ProduitOut> getProductsByFamilly(Long familleId) {

		List<com.webcaisse.ws.model.ProduitOut> produitsVo = new ArrayList<com.webcaisse.ws.model.ProduitOut>();

		List<Produit> produits = productDao.getProductsByFamilly(familleId);
		for (Produit produit : produits) {

			com.webcaisse.ws.model.ProduitOut p = new com.webcaisse.ws.model.ProduitOut();
			p.setLibelle(produit.getLibelle());
			p.setId(produit.getId());
			p.setCouleur(produit.getCouleur());
			p.setCode(produit.getCode());
			produitsVo.add(p);
	
		}

		return produitsVo;
	}

	public ProduitOut loadProductById(Long produitId) {

		ProduitOut produitVo = null;

		Produit produit = productDao.loadProductById(produitId);
		
		if (produit!=null){
			produitVo = new ProduitOut();
			// il faut setter les attributs de ProduitOut
			produitVo.setLibelle(produit.getLibelle());
			produitVo.setCode(produit.getCode());
			produitVo.setId(produit.getId());
			produitVo.setCouleur(produit.getCouleur());
			produitVo.setFamilleId(produit.getFamille().getId());
			List<PrixOut> prixOuts = new ArrayList<PrixOut>();
			produitVo.setPrixOut(prixOuts);
			
			// mapping List<Prix> vers List<PrixOut>
			List<Prix> listPrix  = produit.getPrix();
			for (Prix prix : listPrix) {
				PrixOut prixOut = new PrixOut();
				
				//recuperer le prix unitaire 
				prixOut.setValeur(prix.getPrix());
				
				prixOut.setIdPrix(prix.getId());
				
				// ajout de prixOut a la list
				produitVo.getPrixOut().add(prixOut);
			}
		}

		return produitVo;

	}

	public Long sauvegarderCommande(CommandeIn in ) {
		double montant=0D ;
		
		List<LigneCommandeIn> listLigneCommandeIn  = in.getLignesCommandesIn() ;
	
		List<LigneCommande> lc = new ArrayList<LigneCommande> ();
		Session session = sessionDao.loadSessionById(in.getIdSession());
		
		ClientIn clientIn= in.getClientIn() ;
		Client client = new Client() ;
		
		client.setNom(clientIn.getNom());
		client.setPrenom(clientIn.getPrenom());
		client.setImmeuble(clientIn.getImmeuble());
		client.setEtage(clientIn.getEtage());
		client.setInterphone(clientIn.getInterphone());
		client.setNomVoie(clientIn.getNomRue());
		client.setNumeroRue(clientIn.getNumeroRue());
		client.setSociete(session.getUser().getSociete());
		
//		EtatCommandeIn  etatCommandeIn= in.getEtatCommandeIn() ;
//		EtatCommande etatCommande= new EtatCommande() ;
//		etatCommande.setCode(etatCommandeIn.getCode());
		
		
		
		Commande commande = new  Commande() ;
		commande.setDateCommande(new Date());
		
		
		commande.setSociete(session.getUser().getSociete());
		
		commande.setSession(session);

			
		for (LigneCommandeIn ligneCommandeIn : listLigneCommandeIn)	{
	
			
			LigneCommande ligneCommande= new LigneCommande() ;	
		
		    ligneCommande.setPrix(ligneCommandeIn.getPrix());
		    ligneCommande.setCommande(commande);
		    
		    // en ffaite ici il faut appler le dao productDao pour obtenit le produit a partir de son id  et le setter apres dans la commande
		    Produit produit = productDao.loadProductById(ligneCommandeIn.getIdProduit());
		    ligneCommande.setProduit(produit);
		    ligneCommande.setQte(ligneCommandeIn.getQuantite());
		    ligneCommande.setTotale(ligneCommandeIn.getPrix()*ligneCommandeIn.getQuantite());
		    montant+=ligneCommande.getTotale() ;
	        lc.add(ligneCommande) ;
		
		}
		
		
		commande.setLigneCommandes(lc);
		commande.setMode(in.getMode());
		commande.setMontant(montant);
		commande.setCommentaire(in.getNotes());
		commande.setClient(client);
		
		return  productDao.sauvegarderCommande(commande) ;
		
		
	}

	public void ajouterProduit(ProduitIn p) {
		
		Produit produit = new Produit() ;
		Famille famille = new Famille() ;
		
		
		produit.setLibelle(p.getLibelle());
		produit.setCode(p.getCode());
		famille.setId(p.getFamilleId());
		
		produit.setFamille(famille);
			
		productDao.ajouterProduit(produit);
		
	}

	/**
	 * Supprimer un produit de la BDD
	 */
	public void supprimerProduit(Long idProduit) {
		
		productDao.supprimerProduit(idProduit);
		
	}

	public void updateProduit(ProduitIn p) {
		
		Produit produit = productDao.loadProductById(p.getId());
	
       produit.setLibelle(p.getLibelle());
       produit.setCode(p.getCode());

		
		productDao.updateProduit(produit);
		
	}

	public void ajouterFamille(FamilleIn f) {
		
		Famille famille = new Famille () ;
		Societe societe = new Societe() ;
		societe.setId(f.getIdSociete());
		famille.setLibelle(f.getLibelle());
		famille.setCouleur(f.getColor());
		famille.setSociete(societe);
		
	    productDao.ajouterFamille(famille);
			
	}

	public void supprimerFamille(Long idFamille) {
	
		productDao.supprimerFamille(idFamille);
	}

	public void updateFamille(FamilleIn f) {
		
		Famille  famille = productDao.loadFamilleById(f.getId()) ;
		famille.setLibelle(f.getLibelle());
		famille.setCouleur(f.getColor());
		
		productDao.updateFamille(famille);
	}

	public FamilleOut loadFamilleById(Long IdFamille) {
		
		FamilleOut familleVo = null;

		Famille famille = productDao.loadFamilleById(IdFamille);
		
		if (famille!=null){
			familleVo = new FamilleOut();
			
			familleVo.setLibelle(famille.getLibelle()) ;
			familleVo.setCouleur(famille.getCouleur());
		
	}

	return familleVo ;
	}

	public List<DetailsModePaiementOut> afficherDetailesModePaiement(
			DetailsModePaiementIn in) {
		
		List<DetailsModePaiementOut> detailsModePaiementOut = new ArrayList<DetailsModePaiementOut>();
		
		Double montantEspece = 0D;
		Double montantCb = 0D;
		Double montantCf = 0D;
		Double montantCheque = 0D;
		Double montantTr = 0D;
		
		List<Commande> commandes  = commandeDao.getordersByDates(in.getDateDebutStats(), in.getDateFinStats(), in.getIdSociete());
		if (commandes!=null){
			for (Commande commande : commandes) {
				montantEspece+=commande.getRegEspece()!=null?commande.getRegEspece():0D;
				montantCb+=commande.getRegCB()!=null?commande.getRegCB():0D;
				montantCf+=commande.getRegCarteFidelite()!=null?commande.getRegCarteFidelite():0D;
				montantCheque+=commande.getRegCheque()!=null?commande.getRegCheque():0D;
				montantTr+=commande.getRegTicketRestau()!=null?commande.getRegTicketRestau():0D;
			}
		}
		detailsModePaiementOut.add(new DetailsModePaiementOut(LIBELLE_ESPECE, montantEspece));
		detailsModePaiementOut.add(new DetailsModePaiementOut(LIBELLE_CB, montantCb));
		detailsModePaiementOut.add(new DetailsModePaiementOut(LIBELLE_CF, montantCf));
		detailsModePaiementOut.add(new DetailsModePaiementOut(LIBELLE_CHEQUE, montantCheque));
		detailsModePaiementOut.add(new DetailsModePaiementOut(LIBELLE_TR, montantTr));
		return detailsModePaiementOut;
	}

	
	
}
