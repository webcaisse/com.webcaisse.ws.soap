package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IParametreDao;
import com.webcaisse.dao.hibernate.ISocieteDao;
import com.webcaisse.dao.hibernate.model.Reference;
import com.webcaisse.dao.hibernate.model.Societe;
import com.webcaisse.ws.interfaces.ParametreManagerService;
import com.webcaisse.ws.model.ParametreIn;

public class ParametreManagerServiceImpl implements ParametreManagerService {

	@Autowired
	IParametreDao parametreDao;
	
	@Autowired
	ISocieteDao societeDao ;
	
	public void sauvegarderParametre(ParametreIn parametre) {
		
	
			Reference reference = new Reference () ; 
		
			Societe societe = societeDao.loadById(parametre.getIdSociete());
			
			//reference.setDateCrea(parametre.getDateCrea());
			//reference.setDateModif(parametre.getDateModif());
			reference.setNomParametre(parametre.getNomParametre());
			//reference.setValeur(parametre.getValeur());
			reference.setSociete(societe);
			
			parametreDao.sauvgarderParametre(reference);
			
		
		
			
		
	}

}
