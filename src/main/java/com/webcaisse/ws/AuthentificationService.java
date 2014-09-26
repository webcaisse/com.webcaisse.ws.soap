package com.webcaisse.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.webcaisse.ws.model.UserVo;


@WebService
public interface AuthentificationService {
	
	@WebMethod
	public UserVo getAuthentification(String userName, String password, String codeSociete ) ;
}
