package com.webcaisse.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.webcaisse.dao.hibernate.IClient;
import com.webcaisse.dao.hibernate.model.Client;
import com.webcaisse.ws.interfaces.ClientManagerService;
import com.webcaisse.ws.model.ClientOut;

public class ClientManagerServiceImpl implements ClientManagerService {

	@Autowired
	IClient clientDao;

	public List<ClientOut> rechercherClient(Long idSociete) {

		List<ClientOut> clientVo = new ArrayList<ClientOut>();

		List<Client> clients = clientDao.rechercherClient(idSociete);
		ClientOut c = new ClientOut();

		for (Client client : clients) {

			c.setEmail(client.getEmail());
			c.setNom(client.getNom());
			c.setPrenom(client.getPrenom());
			c.setTelephone(client.getTelephone());
			clientVo.add(c);
		}

		return clientVo;
	}

}
