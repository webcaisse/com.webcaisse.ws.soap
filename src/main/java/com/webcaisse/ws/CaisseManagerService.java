package com.webcaisse.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.webcaisse.dao.hibernate.model.Famille;
import com.webcaisse.dao.hibernate.model.Produit;


@WebService
public interface CaisseManagerService {

	
	@WebMethod
	public List<Famille> getFamillesActivees ();
	
	@WebMethod 
	public List<Produit> getProduitParFamilleReference (String reference);
}
