package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.ICommandeDao;
import com.webcaisse.dao.hibernate.ISessionDao;
import com.webcaisse.dao.hibernate.model.Client;
import com.webcaisse.dao.hibernate.model.Commande;
import com.webcaisse.dao.hibernate.model.EtatCommande;
import com.webcaisse.dao.hibernate.model.LigneCommande;
import com.webcaisse.dao.hibernate.model.Livreur;
import com.webcaisse.ws.interfaces.CommandeManagerService;
import com.webcaisse.ws.model.ClientOut;
import com.webcaisse.ws.model.CommandeOut;
import com.webcaisse.ws.model.LigneCommandeOut;
import com.webcaisse.ws.model.PrixOut;

public class CommandeManagerServiceImpl implements CommandeManagerService {

	@Autowired
	ISessionDao sessionDao;
	
	@Autowired
	ICommandeDao commandeDao ; 

	public List<CommandeOut> rechercherCommande(Long idSession) {

		List<CommandeOut> commandeVo = new ArrayList<CommandeOut>();

		List<Commande> commandes = sessionDao.rechercherCommande(idSession);

		pouplateCommandeOut(commandeVo, commandes);

		return commandeVo;
	}

	public List<CommandeOut> rechercherCommandeParDate(Long idSociete,
			Date dateCommande) {
		List<CommandeOut> commandeVo = new ArrayList<CommandeOut>();

		List<Commande> commandes = sessionDao.rechercherCommandeParDate(
				idSociete, dateCommande);

		pouplateCommandeOut(commandeVo, commandes);
		return commandeVo;
	}

	private void pouplateCommandeOut(List<CommandeOut> commandeVo,List<Commande> commandes) {
		//CommandeOut c = new CommandeOut();

		StringBuffer sb = new StringBuffer();
		int index=1 ;

		for (Commande commande : commandes) {
			CommandeOut c = new CommandeOut();
			
			List<LigneCommande> ligneCommandes = commande.getLigneCommandes();
			for (LigneCommande ligneCommande : ligneCommandes) {
				sb.append("(" +index+") ");
				sb.append(ligneCommande.getProduit().getLibelle()).append(" ");
				index++ ;
			}

			c.setLibelleProduit(sb.toString());
			c.setDateCommande(commande.getDateCommande());
			//c.setEtat(commande.getEtat());
			c.setId(commande.getId());
			c.setNomLivreur(commande.getLivreur()!=null?commande.getLivreur().getNom():null) ;
			c.setEtat(commande.getEtatCommande()!=null?commande.getEtatCommande().getCode():null);
			commandeVo.add(c);
			sb.delete(0,sb.length());index=1 ;
		}
	}

	public CommandeOut loadCommandeById(Long idCommande) {
	 
      CommandeOut  commandeVo = null ;
		
		Commande commande = commandeDao.loadCommandeById(idCommande) ;
		
		List<LigneCommandeOut> ligneCommandeOuts = new ArrayList<LigneCommandeOut>();
		
		
		if (commande != null){
			
			commandeVo=new CommandeOut() ;
			commandeVo.setLigneCommandeOut(ligneCommandeOuts);
			List<LigneCommande> ligneCommandes = commande.getLigneCommandes();
			
			
			for (LigneCommande ligneCommande : ligneCommandes) {
				
				LigneCommandeOut ligneCommandeOut = new LigneCommandeOut() ; 
				 
				  ligneCommandeOut.setQuantite(ligneCommande.getQte());
				  ligneCommandeOut.setLibelle(ligneCommande.getProduit().getLibelle());
				  ligneCommandeOut.setPrixUnitaire(ligneCommande.getPrix());
				
				commandeVo.getLigneCommandeOut().add(ligneCommandeOut) ;
			}
			
			    commandeVo.setMode(commande.getMode());
			    commandeVo.setNomLivreur(commande.getLivreur().getNom());
			
		}
		
		
		return commandeVo;
	}

	public List<CommandeOut> getCommandesByIdLivreur(Long idLivreur) {
		
		    List<CommandeOut> commandeVo = new ArrayList<CommandeOut>();
			
			List<Commande> commandes = commandeDao.getCommandesByIdLivreur(idLivreur) ;
			
			pouplateCommandeOut(commandeVo, commandes);
			return commandeVo;
	}

	// c c bien
	public List<CommandeOut>  getCommandesByEtat(String etatCommande)  {
		List<CommandeOut> commandeVo = new ArrayList<CommandeOut>();
		
		List<Commande> commandes = commandeDao.getCommandesByEtat(etatCommande) ;
		
		pouplateCommandeOut(commandeVo, commandes);
		return commandeVo;
	}

	
	
	
	// ca on a pas besoin
	public void affecterEtatToCommande(String etatCommande ,Long idCommande) {
		
		EtatCommande ew=commandeDao.loadEtatCommandeByCode(etatCommande) ;

		Commande  commande = commandeDao.loadCommandeById(idCommande) ;
       
		if(ew!=null){
		commande.setEtatCommande(ew);
		commandeDao.updateCommande(commande);
		}
	
	}
	
	
}
