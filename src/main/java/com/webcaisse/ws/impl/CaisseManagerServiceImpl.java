package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IProductDao;
import com.webcaisse.dao.hibernate.model.Commande;
import com.webcaisse.dao.hibernate.model.Famille;
import com.webcaisse.dao.hibernate.model.LigneCommande;
import com.webcaisse.dao.hibernate.model.Prix;
import com.webcaisse.dao.hibernate.model.Produit;
import com.webcaisse.dao.hibernate.model.Session;
import com.webcaisse.dao.hibernate.model.Societe;
import com.webcaisse.dao.hibernate.model.User;
import com.webcaisse.ws.interfaces.CaisseManagerService;
import com.webcaisse.ws.model.CommandeIn;
import com.webcaisse.ws.model.LigneCommandeIn;
import com.webcaisse.ws.model.PanierOut;
import com.webcaisse.ws.model.PrixOut;
import com.webcaisse.ws.model.ProduitIn;
import com.webcaisse.ws.model.ProduitOut;

public class CaisseManagerServiceImpl implements CaisseManagerService {

	@Autowired
	IProductDao productDao;

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

	public Long sauvegarderCommande(CommandeIn in) {
		
		List<LigneCommandeIn> listLigneCommandeIn  = in.getLignesCommandesIn() ;
	
		List<LigneCommande> lc = new ArrayList<LigneCommande> ();
		
		
		
		Commande commande = new  Commande() ;
		Societe societe = new Societe ();
		societe.setId(in.getIdSociete());
		commande.setSociete(societe);
		
		Session session = new Session();
		session.setSociete(societe);
		User user = new User();
		user.setId(in.getIdUser());
		user.setSociete(societe);
		session.setUser(user);
		//session.setId(in.getIdSession());
		commande.setSession(session);
		for (LigneCommandeIn ligneCommandeIn : listLigneCommandeIn)	{
	
		
			LigneCommande ligneCommande= new LigneCommande() ;
			
			
		ligneCommande.setPrix(ligneCommandeIn.getPrix());
		ligneCommande.setQte(ligneCommandeIn.getQuantite());
		
	
		//ligneCommande.setCommande(commande);
	     lc.add(ligneCommande) ;
		//ligneCommande.setProduit(ligneCommandeIn.getIdProduit());		
		//commande.getLigneCommandes().add(ligneCommande) ;
		
		}
		
		
		commande.setLigneCommandes(lc);
		return  productDao.sauvegarderCommande(commande) ;
		
		
	}


	
	
}
