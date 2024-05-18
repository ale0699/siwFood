package it.uniroma3.siwFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.repository.CookRepository;

@Service
public class CookService {
	
	@Autowired
	private CookRepository cookRepository;
	
	public List<Cook> findAllCooks(){
		
		return (List<Cook>) this.cookRepository.findAll();
	}
	
	public Cook findCookByCredentials(Long idCredentials) {
		
		return this.cookRepository.findByCredentialsIdCredentials(idCredentials);
	}
	
	public void saveCook(Cook cook) {
		
		this.cookRepository.save(cook);
	}
}
