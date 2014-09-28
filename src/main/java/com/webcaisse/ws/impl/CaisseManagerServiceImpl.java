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
		List<com.webcaisse.ws.model.FamilleOut> famillesVo  = new ArrayList<com.webcaisse.ws.model.FamilleOut>();
		List<Famille> familles  = productDao.getFamillies();
		for (Famille famille : familles) {
			com.webcaisse.ws.model.FamilleOut fam = new com.webcaisse.ws.model.FamilleOut();
			fam.setLibelle(famille.getLibelle());
			famillesVo.add(fam);
		}
		return famillesVo;
	}

	public List<com.webcaisse.ws.model.FamilleOut> getProduitParFamilleReference(String reference) {

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

		//return null;// Arrays.asList(new Produit[]{prod1,prod2});
		
		// pas d'implementation pour l'instant
		
		return null;
	}
	public Long ajouterProduit(ProduitIn p, Long idMenu){
		//return productDao.ajouterProduit(p, idMenu);
		return null;
	}
	public List<ProduitOut> getProductsByFamilly (Long familleId){
		
		
		List<com.webcaisse.ws.model.ProduitOut> produitsVo  = new ArrayList<com.webcaisse.ws.model.ProduitOut>();
		List<Produit> produits  = productDao.getProductsByFamilly(familleId);
		for (Produit produit : produits) {
			com.webcaisse.ws.model.ProduitOut p = new com.webcaisse.ws.model.ProduitOut();
			p.setDescription(produit.getDescription());
			p.setLibelle(produit.getLibelle());
			p.setQteStock(produit.getQteStock());
			
			List<PrixOut> prixOut  = new ArrayList<PrixOut>();
			for (Prix prix : produit.getPrix()) {
				
				PrixOut po  = new PrixOut();
				po.setValeur(prix.getPrix());
				prixOut.add(po);
			}
			p.setPrix(prixOut);
			produitsVo.add(p);
		}
		return produitsVo;
	}
		
	}
	
	

