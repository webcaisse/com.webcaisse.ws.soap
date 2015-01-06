package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IParametreDao;
import com.webcaisse.dao.hibernate.ISocieteDao;
import com.webcaisse.dao.hibernate.model.Profil;
import com.webcaisse.dao.hibernate.model.Reference;
import com.webcaisse.dao.hibernate.model.Societe;
import com.webcaisse.dao.hibernate.model.User;
import com.webcaisse.ws.interfaces.ParametreManagerService;
import com.webcaisse.ws.model.ParametreIn;
import com.webcaisse.ws.model.UserIn;
import com.webcaisse.ws.model.UserOut;

public class ParametreManagerServiceImpl implements ParametreManagerService {

	@Autowired
	IParametreDao parametreDao;

	@Autowired
	ISocieteDao societeDao;

	public void sauvegarderParametre(ParametreIn parametre) {

		// je verifie si ce parametre existe deja dans la base
		Reference reference = parametreDao.getReferenceByName(parametre.getNomParametre());
		if (reference == null) {
			// création
			reference = new Reference();
			reference.setDateCrea(new Date());
			reference.setNomParametre(parametre.getNomParametre());
			reference.setValeur(parametre.getValeur());
			reference.setSociete(societeDao.loadById(parametre.getIdSociete()));
		} else {
			// modification
			reference.setValeur(parametre.getValeur());
		}
		parametreDao.sauvgarderParametre(reference);

	}

	public void sauvegarderUser(UserIn userIn) {
		
		User user = new User () ;
		Societe societe = new Societe () ;
		societe.setId(userIn.getSocieteId());
		
		Profil profil = new Profil();
		profil.setId(userIn.getProfil());
		
		user.setNom(userIn.getNom());
		user.setPrenom(userIn.getPrenom()) ;
		user.setAdresse(userIn.getAdresse());
		user.setTelephone(userIn.getTelephone());
		user.setUsername(userIn.getUsername());
		user.setPassword(userIn.getPassword());
		user.setSociete(societe);
		user.setProfil(profil);
		user.setActif(userIn.getEnabled());
		parametreDao.sauvegarderUser(user);
		
	}

	public List<UserOut> rechercherUser(Long idSociete) {
		List<UserOut> userVo = new ArrayList<UserOut>();
		List<User> users = parametreDao.rechercherUser(idSociete) ;
		
		for (User user : users) {
			UserOut  userOut = new UserOut() ;
			
			userOut.setNom(user.getNom());
			userOut.setPrenom(user.getPrenom());
			userOut.setAdresse(user.getAdresse());
			
			userVo.add(userOut) ;
		}
		
		
		return userVo;
	}

}
