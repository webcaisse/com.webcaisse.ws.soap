package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IProductDao;
import com.webcaisse.dao.hibernate.ISessionDao;
import com.webcaisse.dao.hibernate.ISocieteDao;
import com.webcaisse.dao.hibernate.model.Commande;
import com.webcaisse.dao.hibernate.model.Famille;
import com.webcaisse.dao.hibernate.model.LigneCommande;
import com.webcaisse.dao.hibernate.model.Prix;
import com.webcaisse.dao.hibernate.model.Produit;
import com.webcaisse.dao.hibernate.model.Session;
import com.webcaisse.ws.interfaces.CaisseManagerService;
import com.webcaisse.ws.model.CommandeIn;
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

	public List<com.webcaisse.ws.model.FamilleOut> getProduitParFamilleReference(
			String reference) {

		System.out.println("je suis la dans le webservice");
		// Famille famille = new Famille("Pizza",reference);
		//
		// Produit prod1 = new Produit();
		// Produit prod2 = new Produit();
		//
		// prod1.setFamille(famille);
		// prod1.setLibelle("Margeretta");
		// prod1.setPrix(12D);
		//
		//
		// prod2.setFamille(famille);
		// prod2.setLibelle("Napolitaine");
		// prod2.setPrix(20D);

		// return null;// Arrays.asList(new Produit[]{prod1,prod2});

		// pas d'implementation pour l'instant

		return null;
	}

	public Long ajouterProduit(ProduitIn p, Long idMenu) {
		// return productDao.ajouterProduit(p, idMenu);
		return null;
	}

	public List<ProduitOut> getProductsByFamilly(Long familleId) {

		List<com.webcaisse.ws.model.ProduitOut> produitsVo = new ArrayList<com.webcaisse.ws.model.ProduitOut>();

		List<Produit> produits = productDao.getProductsByFamilly(familleId);
		for (Produit produit : produits) {

			com.webcaisse.ws.model.ProduitOut p = new com.webcaisse.ws.model.ProduitOut();
			p.setLibelle(produit.getLibelle());
			p.setId(produit.getId());
			p.setCouleur(produit.getCouleur());

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
			produitVo.setId(produit.getId());
			produitVo.setCouleur(produit.getCouleur());
			
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

	public Long sauvegarderCommande(CommandeIn in    ) {
		
		List<LigneCommandeIn> listLigneCommandeIn  = in.getLignesCommandesIn() ;
	
		List<LigneCommande> lc = new ArrayList<LigneCommande> ();
		
		Session session = sessionDao.loadSessionById(in.getIdSession());
		
		Commande commande = new  Commande() ;
		commande.setDateCommande(new Date());
		
		//Societe societe = societeDao.loadById(in.getIdSociete());
		
		// load societe par son id
		commande.setSociete(session.getSociete());
		
		
	//	session.setSociete(societe);
//		User user = new User();
//		user.setId(in.getIdUser());
//		user.setSociete(societe);
//		session.setUser(user);
		//session.setId(in.getIdSession());
		commande.setSession(session);
		
//		Produit produit = new Produit() ;
		
		
	
		for (LigneCommandeIn ligneCommandeIn : listLigneCommandeIn)	{
	
			
			LigneCommande ligneCommande= new LigneCommande() ;	
		
		    ligneCommande.setPrix(ligneCommandeIn.getPrix());
		    ligneCommande.setCommande(commande);
		    
		    // en ffaite ici il faut appler le dao productDao pour obtenit le produit a partir de son id  et le setter apres dans la commande
		    Produit produit = productDao.loadProductById(ligneCommandeIn.getIdProduit());
		    ligneCommande.setProduit(produit);
		    ligneCommande.setQte(ligneCommandeIn.getQuantite());
		    ligneCommande.setTotale(ligneCommandeIn.getPrix()*ligneCommandeIn.getQuantite());
		    
	        lc.add(ligneCommande) ;
		
		}
		
		
		commande.setLigneCommandes(lc);
		commande.setMode(in.getMode());
		commande.setMontant(in.getMontant());
		commande.setCommentaire(in.getNotes());
		return  productDao.sauvegarderCommande(commande) ;
		
		
	}


	
	
}
