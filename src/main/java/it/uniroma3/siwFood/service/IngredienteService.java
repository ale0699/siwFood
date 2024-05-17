package it.uniroma3.siwFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Ingrediente;
import it.uniroma3.siwFood.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	public List<Ingrediente> findIngredientsByRicettaId(Long idRicetta){
		
		return this.ingredienteRepository.findAllByRicettaIdRicetta(idRicetta);
	}
	
	public Ingrediente findIngredientById(Long idIngrediente) {
		
		return this.ingredienteRepository.findById(idIngrediente).get();
	}
	
	public void saveIngredient(Ingrediente ingrediente) {
		
		this.ingredienteRepository.save(ingrediente);
	}
	
	public void deleteIngredient(Ingrediente ingrediente) {
		
		this.ingredienteRepository.delete(ingrediente);
	}
}
