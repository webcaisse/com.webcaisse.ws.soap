package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.connection.UserSuppliedConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.webcaisse.dao.hibernate.impl.UserDaoImpl;
import com.webcaisse.dao.hibernate.model.Profil;
import com.webcaisse.ws.interfaces.UserManagerService;
import com.webcaisse.ws.model.ProfilVOut;

public class UserManagerServiceImpl implements UserManagerService {

	@Autowired
	UserDaoImpl userDaoImpl;
	
	@Transactional
	public List<ProfilVOut> getProfils() {
		
		List<ProfilVOut> profilVOuts = new ArrayList<ProfilVOut>();
		List<Profil> profils = userDaoImpl.getProfils();
		if (!CollectionUtils.isEmpty(profils)){
			for (Profil profil : profils) {
				profilVOuts.add(new ProfilVOut(profil.getId(), profil.getLibelle()));
			}
		}
		return profilVOuts;
	}
}
