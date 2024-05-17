package it.uniroma3.siwFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Credenziali;
import it.uniroma3.siwFood.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	
	@Autowired
	private CredenzialiRepository credenzialiRepository;
	
	public Credenziali findCredenzialiByUsername(String username) {
		
		return this.credenzialiRepository.findByUsername(username);
	}
}
