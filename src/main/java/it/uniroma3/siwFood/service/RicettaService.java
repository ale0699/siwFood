package it.uniroma3.siwFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Ricetta;
import it.uniroma3.siwFood.repository.RicettaRepository;

@Service
public class RicettaService {
	
	@Autowired
	private RicettaRepository ricettaRepository;
	
	public List<Ricetta> findAllRicepes(){
		
		return (List<Ricetta>) this.ricettaRepository.findAll();
	}
}
