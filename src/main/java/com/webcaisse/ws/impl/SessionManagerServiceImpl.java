package com.webcaisse.ws.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.webcaisse.dao.hibernate.ISessionDao;
import com.webcaisse.dao.hibernate.model.Session;
import com.webcaisse.ws.interfaces.SessionManagerService;

public class SessionManagerServiceImpl implements SessionManagerService {

	@Autowired
	ISessionDao sessionDao;

	public Long ouvrirSession(Long idUser) {

		// 1 - recuperation de session
		List<Session> sessions = sessionDao.getSessionByUserIdAndDate(idUser,
				new Date());
		if (!CollectionUtils.isEmpty(sessions)) {
			return sessions.get(0).getId();
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
