package it.uniroma3.siwFood.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.repository.CookRepository;

@Service
public class CookService {
	
	@Autowired
	private CookRepository cookRepository;
	
	@Autowired
	private CredentialsService credentialsService;
	
	public List<Cook> findAllCooks(){
		
		return (List<Cook>) this.cookRepository.findAll();
	}
	
	public boolean existsByNameAndSurnameAndDateBirth(String nome, String cognome, LocalDate dataNascita) {
		
		return this.cookRepository.existsByNameIgnoreCaseAndSurnameIgnoreCaseAndDateBirth(nome, cognome, dataNascita);
	}
	
	public Cook findCookByIdCook(Long idCook) {
		
		return this.cookRepository.findById(idCook).get();
	}
	
	public Cook findCookByCredentials(Long idCredentials) {
		
		return this.cookRepository.findByCredentialsIdCredentials(idCredentials);
	}
	
	public void saveCook(Cook cook) {
		
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credenziali = this.credentialsService.findCredenzialiByUsername(user.getUsername());
		Cook cookLoggato = this.findCookByCredentials(credenziali.getIdCredentials());
		
		//se l'utente attuale è l'admin oppure è lui stesso, si può salvare un cuoco
		if( (cook==null && credenziali.getRole().equals("ADMIN")) ||  cookLoggato.equals(cook)) {
			
			this.cookRepository.save(cook);
		}
		else {
			
			throw new AccessDeniedException("Access Denied");
		}
	}
	
	public void removeCook(Cook cook) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credenziali = this.credentialsService.findCredenzialiByUsername(user.getUsername());
		Cook cookLoggato = this.findCookByCredentials(credenziali.getIdCredentials());
		
		//se l'utente attuale è l'admin oppure è lui stesso, si può eliminare un cuoco
		if( (cook==null && credenziali.getRole().equals("ADMIN")) ||  cookLoggato.equals(cook)) {
			
			this.cookRepository.delete(cook);
		}
		else {
			
			throw new AccessDeniedException("Access Denied");
		}
	}
}
