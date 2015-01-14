package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.ICommandeDao;
import com.webcaisse.dao.hibernate.ILivreurDao;
import com.webcaisse.dao.hibernate.model.Commande;
import com.webcaisse.dao.hibernate.model.Livreur;
import com.webcaisse.dao.hibernate.model.Societe;
import com.webcaisse.dao.hibernate.model.User;
import com.webcaisse.ws.interfaces.LivreurManagerService;
import com.webcaisse.ws.model.CommandeOut;
import com.webcaisse.ws.model.LivreurIn;
import com.webcaisse.ws.model.LivreurOut;
import com.webcaisse.ws.model.UserOut;

public class LivreurManagerServiceImpl implements LivreurManagerService {

	
	
	@Autowired
	ILivreurDao livreurDao ;
	
	@Autowired
	ICommandeDao commandeDao ;
	
	public void sauvegarderLivreur(LivreurIn livreur) {
		Livreur l = new Livreur() ;
		Societe societe = new Societe() ;
		societe.setId(livreur.getSocieteId());
		l.setNom(livreur.getNom());
		l.setPrenom(livreur.getPrenom());
		l.setTelephone(livreur.getTelephone());
		l.setAdresse(livreur.getAdresse());
		l.setNss(livreur.getNss());
		l.setSociete(societe);
		
		livreurDao.sauvegarderLivreur(l);
		
	}

	public List<LivreurOut> rechercherLivreur(Long idSociete) {
		
		List<LivreurOut> livreurVo = new ArrayList<LivreurOut>();
		List<Livreur> livreurs = livreurDao.rechercherLivreur(idSociete) ;
		
		for (Livreur livreur : livreurs) {
			LivreurOut  livreurOut = new LivreurOut() ;
			
			livreurOut.setNom(livreur.getNom());
			livreurOut.setPrenom(livreur.getPrenom());
			livreurOut.setAdresse(livreur.getAdresse());
			livreurOut.setId(livreur.getId());
			
			livreurVo.add(livreurOut) ;
		}
		
		
		return livreurVo;
	}

	public void affecterLivreurToCommande(Long idLivreur ,Long idCommande) {
	
		Livreur livreur = livreurDao.loadLivreurById(idLivreur) ;

		Commande  commande = commandeDao.loadCommandeById(idCommande) ;
	
		commande.setLivreur(livreur);
		commandeDao.updateCommande(commande);
		
	
	}
	}


