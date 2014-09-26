package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IProductDao;
import com.webcaisse.dao.hibernate.model.Famille;
import com.webcaisse.dao.hibernate.model.Produit;
import com.webcaisse.ws.CaisseManagerService;

public class CaisseManagerServiceImpl implements CaisseManagerService {

	@Autowired
	IProductDao productDao;

	public List<com.webcaisse.ws.model.Famille> getFamillesActivees() {
		List<com.webcaisse.ws.model.Famille> famillesVo  = new ArrayList<com.webcaisse.ws.model.Famille>();
		List<Famille> familles  = productDao.getFamillies();
		for (Famille famille : familles) {
			com.webcaisse.ws.model.Famille fam = new com.webcaisse.ws.model.Famille();
			fam.setLibelle(famille.getLibelle());
			famillesVo.add(fam);
		}
		return famillesVo;
	}

	public List<com.webcaisse.ws.model.Famille> getProduitParFamilleReference(String reference) {

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
	public Long ajouterProduit(Produit p, Long idMenu){
		return productDao.ajouterProduit(p, idMenu);
		
	}
	
	
}
