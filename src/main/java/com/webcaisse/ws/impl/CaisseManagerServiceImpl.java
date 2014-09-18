package com.webcaisse.ws.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IProductDao;
import com.webcaisse.dao.hibernate.model.Famille;
import com.webcaisse.ws.CaisseManagerService;

public class CaisseManagerServiceImpl implements CaisseManagerService {

	@Autowired
	IProductDao productDao;

	public List<Famille> getFamillesActivees() {
		return productDao.getFamillies();
	}

	public List<Famille> getProduitParFamilleReference(String reference) {

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

		return null;// Arrays.asList(new Produit[]{prod1,prod2});
	}
}
