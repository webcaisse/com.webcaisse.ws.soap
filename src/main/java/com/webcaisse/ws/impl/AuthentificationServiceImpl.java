package com.webcaisse.ws.impl;

import com.webcaisse.ws.AuthentificationService;

public class AuthentificationServiceImpl implements AuthentificationService {

	@Autowired
	IAuthentification  authentificationDao;
	
	@Transactional
	public UserVo getAuthentification(String userName, String password, String codeSociete) {
		UserVo userVo = null;
		User user =  authentificationDao.getAuthentification( userName, password, codeSociete);
		
		if (user !=null){
			userVo = new UserVo();
			userVo.setNom(user.getNom());
			userVo.setPrenom(user.getPrenom());
			userVo.setAdresse(user.getAdresse());
			userVo.setNomSociete(user.getSociete().getNom());
		}
		return userVo;
	}

	
}
