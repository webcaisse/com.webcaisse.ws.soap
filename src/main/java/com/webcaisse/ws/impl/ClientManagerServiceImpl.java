package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IClient;
import com.webcaisse.dao.hibernate.model.Client;
import com.webcaisse.dao.hibernate.model.Societe;
import com.webcaisse.ws.interfaces.ClientManagerService;
import com.webcaisse.ws.model.ClientIn;
import com.webcaisse.ws.model.ClientOut;

public class ClientManagerServiceImpl implements ClientManagerService {

	@Autowired
	IClient clientDao;

	public List<ClientOut> rechercherClient(Long idSociete) {

		List<ClientOut> clientVo = new ArrayList<ClientOut>();

		List<Client> clients = clientDao.rechercherClient(idSociete);
		

		for (Client client : clients) {
			ClientOut c = new ClientOut();
			c.setEmail(client.getEmail());
			c.setNom(client.getNom());
			c.setPrenom(client.getPrenom());
			c.setTelephone(client.getTelephone());
			c.setId(client.getId());
			c.setCode1(client.getCode1());
			c.setCode2(client.getCode2());
			c.setCode3(client.getCode3());
			c.setCodePostale(client.getCodePostale());
			c.setEtage(client.getEtage());
			c.setImmeuble(client.getImmeuble());
			c.setInterphone(client.getInterphone());
			c.setNumeroRue(client.getNumeroRue());
			c.setNomRue(client.getNomVoie());
			c.setVille(client.getVille());
			
			
			clientVo.add(c);
		}

		return clientVo;
	}

	public void ajouterClient(ClientIn c) {
	
		Client client = new Client() ;
		Societe societe = new Societe() ;
		societe.setId(c.getIdSociete());
		client.setNom(c.getNom());
		client.setPrenom(c.getPrenom());
		client.setEmail(c.getEmail());
		client.setTelephone(c.getTelephone());
		client.setSociete(societe);
		client.setCode1(c.getCode1());
		client.setCode2(c.getCode2());
		client.setCode3(c.getCode3());
		client.setCodePostale(c.getCodePostale());
		client.setEtage(c.getEtage());
		client.setImmeuble(c.getImmeuble());
		client.setInterphone(c.getInterphone());
		client.setNumeroRue(c.getNumeroRue());
		client.setNomVoie(c.getNomRue());
		client.setVille(c.getVille());
		
		
		clientDao.ajouterClient(client) ;
		
	}

	public void supprimerClient(Long idClient) {
		
		clientDao.supprimerClient(idClient);
	}

	public void updateClient(ClientIn c) {
		
		Client client = clientDao.loadClientById(c.getId()) ;
		
		client.setNom(c.getNom());
		client.setPrenom(c.getPrenom());
		client.setEmail(c.getEmail());
		client.setTelephone(c.getTelephone());
		client.setCode1(c.getCode1());
		client.setCode2(c.getCode2());
		client.setCode3(c.getCode3());
		client.setCodePostale(c.getCodePostale());
		client.setEtage(c.getEtage());
		client.setImmeuble(c.getImmeuble());
		client.setInterphone(c.getInterphone());
		client.setNumeroRue(c.getNumeroRue());
		client.setNomVoie(c.getNomRue());
		client.setVille(c.getVille());
		
		clientDao.updateClient(client);
		
	}

	public ClientOut loadClientById(Long IdClient) {
		
		
		ClientOut  clientVo = null ;
		
		Client client = clientDao.loadClientById(IdClient) ;
		if (client != null){
			
			clientVo=new ClientOut() ;
		clientVo.setNom(client.getNom());
		clientVo.setPrenom(client.getPrenom());
		clientVo.setEmail(client.getEmail());
		clientVo.setTelephone(client.getTelephone());
		clientVo.setCode1(client.getCode1());
		clientVo.setCode2(client.getCode2());
		clientVo.setCode3(client.getCode3());
		clientVo.setCodePostale(client.getCodePostale());
		clientVo.setEtage(client.getEtage());
		clientVo.setImmeuble(client.getImmeuble());
		clientVo.setInterphone(client.getInterphone());
		clientVo.setNumeroRue(client.getNumeroRue());
		clientVo.setNomRue(client.getNomVoie());
		clientVo.setVille(client.getVille());
		
					
		}
		
		return clientVo;
	}

}
