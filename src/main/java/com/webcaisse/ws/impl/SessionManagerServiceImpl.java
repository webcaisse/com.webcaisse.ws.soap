package com.webcaisse.ws.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.ISessionDao;
import com.webcaisse.dao.hibernate.model.Session;
import com.webcaisse.ws.interfaces.SessionManagerService;

public class SessionManagerServiceImpl implements SessionManagerService {

	@Autowired
	ISessionDao sessionDao;

	public Long ouvrirSession(Long idUser) {

		// 1 - recuperation de session
		Session session = (Session) sessionDao.getSessionByUserIdAndDate(idUser,new Date());
		if (session != null) {
			return session.getId();
		} else {
			Long idSession = sessionDao.creerSession(idUser, new Date());
			return idSession;
		}
	}

	public void fermerSession(Long idSession) {
		
		Session session = sessionDao.loadSessionById (idSession) ;
		if (session!=null){
			
			session.setEtat('F');
			session.setDateFermeture(new Date());
			sessionDao.updateSession(session);
		}
		
		
	}

}
