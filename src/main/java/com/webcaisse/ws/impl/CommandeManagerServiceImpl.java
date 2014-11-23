package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.ISessionDao;
import com.webcaisse.dao.hibernate.model.Commande;
import com.webcaisse.ws.interfaces.CommandeManagerService;
import com.webcaisse.ws.model.CommandeOut;

public class CommandeManagerServiceImpl implements CommandeManagerService{

	
	@Autowired 
	ISessionDao sessionDao;
	
	public List<CommandeOut> rechercherCommande(Long idSession) {
		
		List<CommandeOut> commandeVo = new ArrayList<CommandeOut>() ;
		
		List<Commande> commandes = sessionDao.rechercherCommande(idSession) ;
		
		for (Commande commande : commandes) {
			
			CommandeOut c = new CommandeOut() ;
			c.setDateCommande(commande.getDateCommande());
			c.setEtat(commande.getEtat());
			
			commandeVo.add(c) ;
		}
		
		
		return commandeVo ;
	}

}
