package com.webcaisse.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.webcaisse.dao.hibernate.model.Famille;
import com.webcaisse.dao.hibernate.model.Produit;


public class CaisseManagerServiceImpl implements CaisseManagerService{

//	@Autowired
//	IProduitDao produitDao;
	
	public List<Famille> getFamillesActivees() {
		List<Famille> familles = new ArrayList<Famille>();
		
	//	List<com.webcaisse.entity.Famille> familles2 = produitDao.getFamillys();
//		familles.add(new Famille("Pizza","001"));
//		familles.add(new Famille("Boissons","002"));
//		familles.add(new Famille("Sandwitch","003"));
//		familles.add(new Famille("Paninis","004"));
//		familles.add(new Famille("Plats","005"));
//		familles.add(new Famille("Menus","006"));
//		familles.add(new Famille("Formules","006"));
		
		return familles;
	}

	public List<Produit> getProduitParFamilleReference(String reference) {
		
		System.out.println("je suis la dans le webservice");
//		Famille famille  = new Famille("Pizza",reference);
//		
//		Produit prod1 = new Produit();
//		Produit prod2 = new Produit();
//		
//		prod1.setFamille(famille);
//		prod1.setLibelle("Margeretta");
//		prod1.setPrix(12D);
//		
//
//		prod2.setFamille(famille);
//		prod2.setLibelle("Napolitaine");
//		prod2.setPrix(20D);
		
		return null;// Arrays.asList(new Produit[]{prod1,prod2});
	}
}
