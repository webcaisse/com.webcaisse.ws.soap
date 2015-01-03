package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.Date;
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
	ISocieteDao societeDao;

	public void sauvegarderParametre(ParametreIn parametre) {

		// je verifie si ce parametre existe deja dans la base
		Reference reference = parametreDao.getReferenceByName(parametre
				.getNomParametre());
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

}
