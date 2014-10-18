package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IProductDao;
import com.webcaisse.dao.hibernate.model.Famille;
import com.webcaisse.dao.hibernate.model.Prix;
import com.webcaisse.dao.hibernate.model.Produit;
import com.webcaisse.ws.interfaces.CaisseManagerService;
import com.webcaisse.ws.model.PrixOut;
import com.webcaisse.ws.model.ProduitIn;
import com.webcaisse.ws.model.ProduitOut;

public class CaisseManagerServiceImpl implements CaisseManagerService {

	@Autowired
	IProductDao productDao;

	public List<com.webcaisse.ws.model.FamilleOut> getFamillesActivees() {
		List<com.webcaisse.ws.model.FamilleOut> famillesVo = new ArrayList<com.webcaisse.ws.model.FamilleOut>();
		List<Famille> familles = productDao.getFamillies();
		for (Famille famille : familles) {
			com.webcaisse.ws.model.FamilleOut fam = new com.webcaisse.ws.model.FamilleOut();
			fam.setLibelle(famille.getLibelle());
			fam.setId(famille.getId());
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
			
			List<PrixOut> prixOuts = new ArrayList<PrixOut>();
			produitVo.setPrixOut(prixOuts);
			
			// mapping List<Prix> vers List<PrixOut>
			List<Prix> listPrix  = produit.getPrix();
			for (Prix prix : listPrix) {
				PrixOut prixOut = new PrixOut();
				
				//recuperer le prix unitaire 
				prixOut.setValeur(prix.getPrix());
				
				// ajout de prixOut a la list
				produitVo.getPrixOut().add(prixOut);
			}
		}

		return produitVo;

	}



//	public PanierOut ajouterProduitAuPanier(ProduitOut p, Long idPanier) {
//		PanierOut panierVo=null ; 
//		
//		Produit produit = new Produit () ;
//		Panier panier = productDao.ajouterProduitAuPanier(produit,idPanier) ;
//		
//		
//			
//		    panierVo= new PanierOut() ;
//			panierVo.setQte(panier.getQte());
//			panierVo.setLibelle(panier.getLibelle());
//			
//			//List<ProduitOut> produitOuts = new ArrayList<ProduitOut>();
//			//panierVo.setProduits((List<ProduitOut>) p);
//			
//			
//			panierVo.getProduits().add(p);
//			
//	
//			
//		return panierVo ;	
//			
//	}

	
	
}
