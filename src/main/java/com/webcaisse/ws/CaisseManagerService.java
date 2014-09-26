package com.webcaisse.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.webcaisse.dao.hibernate.model.Produit;
import com.webcaisse.ws.model.Famille;


@WebService
public interface CaisseManagerService {

	
	@WebMethod
	public List<Famille> getFamillesActivees ();
	
	@WebMethod 
	public List<Famille> getProduitParFamilleReference (String reference);
	
	@WebMethod 
	public Long ajouterProduit(Produit p, Long idMenu) ;
}
