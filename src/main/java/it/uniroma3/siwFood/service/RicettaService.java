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
	
	public List<Ricetta> findAllRecipes(){
		return (List<Ricetta>) this.ricettaRepository.findAll();
	}
	
	public Ricetta findRecipeById(Long idRicetta){
		return this.ricettaRepository.findById(idRicetta).get();
	}
	
	public void saveRecipe(Ricetta ricetta) {
		this.ricettaRepository.save(ricetta);
	}
	
	public List<Ricetta> findRecipesByIngredienteNome(String nomeIngrediente){
		return this.ricettaRepository.findAllByIngredientiNomeContainsAllIgnoreCase(nomeIngrediente);
	}
	
	public List<Ricetta> findRecipesByNome(String nome){
		return this.ricettaRepository.findAllByNomeContainsAllIgnoreCase(nome);
	}
	
	public void deleteRecipe(Ricetta ricetta) {
		this.ricettaRepository.delete(ricetta);
	}

}
