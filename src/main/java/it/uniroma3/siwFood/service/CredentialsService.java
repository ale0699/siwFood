package it.uniroma3.siwFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
	private CredentialsRepository credenzialiRepository;
	
	public Credentials findCredenzialiByUsername(String username) {
		
		return this.credenzialiRepository.findByUsername(username);
	}
	
	public boolean existsByUsername(String username) {
		
		return this.credenzialiRepository.existsByUsername(username);
	}
}
