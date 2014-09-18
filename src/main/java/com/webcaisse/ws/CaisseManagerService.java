package com.webcaisse.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface CaisseManagerService {

	
	@WebMethod
	public List getFamillesActivees ();
	
	@WebMethod 
	public List getProduitParFamilleReference (String reference);
}
