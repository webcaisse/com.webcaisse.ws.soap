package com.webcaisse.ws.impl;

import org.springframework.beans.factory.annotation.Autowired;


import com.webcaisse.dao.hibernate.IAuthentification;
import com.webcaisse.dao.hibernate.model.User;
import com.webcaisse.ws.interfaces.AuthentificationService;
import com.webcaisse.ws.model.UserOut;

public class AuthentificationServiceImpl implements AuthentificationService {

	@Autowired
	IAuthentification  authentificationDao;
	
	
	public UserOut getAuthentification(String userName, String password, String codeSociete) {
		UserOut userVo = null;
		User user =  authentificationDao.getAuthentification( userName, password, codeSociete);
		
		if (user !=null){
			userVo = new UserOut();
			userVo.setNom(user.getNom());
			userVo.setPrenom(user.getPrenom());
			userVo.setAdresse(user.getAdresse());
			userVo.setNomSociete(user.getSociete().getNom());
		}
		return userVo;
	}

   public UserOut findByUserName(String userName){
	   UserOut userVo = null;
	   User user = authentificationDao.finByUserName(userName) ;
	   
	   if (user !=null){
			userVo = new UserOut();
			userVo.setUsername(user.getUsername());
			userVo.setPassword(user.getPassword());
			userVo.setSocieteId(user.getSociete().getId());
			userVo.setId(user.getId());
		}
		return userVo;
	   

   }	   
	   
	   
	   
   }
	
	
	

