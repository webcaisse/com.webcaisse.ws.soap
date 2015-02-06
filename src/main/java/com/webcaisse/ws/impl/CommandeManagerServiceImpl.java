package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.ICommandeDao;
import com.webcaisse.dao.hibernate.ISessionDao;
import com.webcaisse.dao.hibernate.model.Commande;
import com.webcaisse.dao.hibernate.model.LigneCommande;
import com.webcaisse.ws.interfaces.CommandeManagerService;
import com.webcaisse.ws.model.CommandeOut;
import com.webcaisse.ws.model.LigneCommandeOut;

public class CommandeManagerServiceImpl implements CommandeManagerService {

	@Autowired
	ISessionDao sessionDao;

	@Autowired
	ICommandeDao commandeDao;

	public List<CommandeOut> rechercherCommande(Long idSession) {

		List<CommandeOut> commandeVo = new ArrayList<CommandeOut>();

		List<Commande> commandes = sessionDao.rechercherCommande(idSession);

		pouplateCommandeOut(commandeVo, commandes);

		return commandeVo;
	}

	public List<CommandeOut> rechercherCommandeParDate(Long idSociete, Date dateCommande) {
		List<CommandeOut> commandeVo = new ArrayList<CommandeOut>();

		List<Commande> commandes = sessionDao.rechercherCommandeParDate(idSociete, dateCommande);

		pouplateCommandeOut(commandeVo, commandes);
		return commandeVo;
	}

	public CommandeOut loadCommandeById(Long idCommande) {

		CommandeOut commandeVo = null;

		Commande commande = commandeDao.loadCommandeById(idCommande);

		List<LigneCommandeOut> ligneCommandeOuts = new ArrayList<LigneCommandeOut>();

		if (commande != null) {

			commandeVo = new CommandeOut();
			commandeVo.setLigneCommandeOut(ligneCommandeOuts);
			List<LigneCommande> ligneCommandes = commande.getLigneCommandes();

			for (LigneCommande ligneCommande : ligneCommandes) {

				LigneCommandeOut ligneCommandeOut = new LigneCommandeOut();

				ligneCommandeOut.setQuantite(ligneCommande.getQte());
				ligneCommandeOut.setLibelle(ligneCommande.getProduit()!=null?ligneCommande.getProduit()
						.getLibelle():null);
				ligneCommandeOut.setPrixUnitaire(ligneCommande.getPrix());

				commandeVo.getLigneCommandeOut().add(ligneCommandeOut);
			}

			commandeVo.setMode(commande.getMode());
			commandeVo.setNomLivreur(commande.getLivreur()!=null?commande.getLivreur().getNom():null);

		}

		return commandeVo;
	}

	public List<CommandeOut> getCommandesByIdLivreur(Long idLivreur) {

		List<CommandeOut> commandeVo = new ArrayList<CommandeOut>();

		List<Commande> commandes = commandeDao.getCommandesByIdLivreur(idLivreur);

		pouplateCommandeOut(commandeVo, commandes);
		return commandeVo;
	}

	private void pouplateCommandeOut(List<CommandeOut> commandeVo, List<Commande> commandes) {

		StringBuffer sb = new StringBuffer();
		int index = 1;

		for (Commande commande : commandes) {
			CommandeOut c = new CommandeOut();

			List<LigneCommande> ligneCommandes = commande.getLigneCommandes();
			for (LigneCommande ligneCommande : ligneCommandes) {
				sb.append("(" + index + ") ");
				sb.append(ligneCommande.getProduit().getLibelle()).append(" ");
				index++;
			}
			c.setLibelleProduit(sb.toString());
			c.setDateCommande(commande.getDateCommande());
			c.setEtat(commande.getEtat());
			c.setId(commande.getId());
			c.setNomLivreur(commande.getLivreur() != null ? commande
					.getLivreur().getNom() : null);
			commandeVo.add(c);
			sb.delete(0, sb.length());
			index = 1;
		}
	}


}
