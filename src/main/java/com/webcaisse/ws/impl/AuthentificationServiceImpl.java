package com.webcaisse.ws.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.webcaisse.dao.hibernate.IAuthentification;
import com.webcaisse.dao.hibernate.model.User;
import com.webcaisse.ws.AuthentificationService;
import com.webcaisse.ws.model.UserVo;

public class AuthentificationServiceImpl implements AuthentificationService {

	@Autowired
	IAuthentification  authentificationDao;
	
	@Transactional
	public UserVo getAuthentification(String userName, String password, String codeSociete) {
		UserVo userVo = null;
		User user =  authentificationDao.getAuthentification( userName, password, codeSociete);
		
		if (user !=null){
			userVo = new UserVo();
			userVo.setNom(user.getNom());
			userVo.setPrenom(user.getPrenom());
			userVo.setAdresse(user.getAdresse());
			userVo.setNomSociete(user.getSociete().getNom());
		}
		return userVo;
	}

	
}
