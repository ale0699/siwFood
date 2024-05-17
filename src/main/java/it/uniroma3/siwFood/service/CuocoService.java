package it.uniroma3.siwFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Cuoco;
import it.uniroma3.siwFood.repository.CuocoRepository;

@Service
public class CuocoService {
	
	@Autowired
	private CuocoRepository cuocoRepository;
	
	public List<Cuoco> findAllCooks(){
		
		return (List<Cuoco>) this.cuocoRepository.findAll();
	}
	
	public Cuoco findCookByCredenziali(Long idCredenziali) {
		
		return this.cuocoRepository.findByCredenzialiIdCredenziali(idCredenziali);
	}
	
	public void saveCook(Cuoco cuoco) {
		
		this.cuocoRepository.save(cuoco);
	}
}
