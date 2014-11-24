package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.ISessionDao;
import com.webcaisse.dao.hibernate.model.Commande;
import com.webcaisse.dao.hibernate.model.LigneCommande;
import com.webcaisse.dao.hibernate.model.Produit;
import com.webcaisse.ws.interfaces.CommandeManagerService;
import com.webcaisse.ws.model.CommandeOut;

public class CommandeManagerServiceImpl implements CommandeManagerService{

	
	@Autowired 
	ISessionDao sessionDao;
	
	public List<CommandeOut> rechercherCommande(Long idSession) {
		
		List<CommandeOut> commandeVo = new ArrayList<CommandeOut>() ;
		
		List<Commande> commandes = sessionDao.rechercherCommande(idSession) ;
		
		CommandeOut c = new CommandeOut() ;
		
		StringBuffer sb = new StringBuffer() ;
		for (Commande commande : commandes) {
			  List<LigneCommande> ligneCommandes = commande.getLigneCommandes() ;
				  for (LigneCommande ligneCommande : ligneCommandes) {
					  
					   sb.append(ligneCommande.getProduit().getLibelle()).append(" ") ;
					 
					  
				}
				        
				c.setLibelleProduit(sb.toString());
				c.setDateCommande(commande.getDateCommande());
				c.setEtat(commande.getEtat());
				commandeVo.add(c) ;
			}
			
			
		
		
		
		return commandeVo ;
	}

}
