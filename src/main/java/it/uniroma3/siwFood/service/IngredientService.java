package it.uniroma3.siwFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Ingredient;
import it.uniroma3.siwFood.repository.IngredientRepository;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	public List<Ingredient> findIngredientsByRecipeId(Long idRecipe){
		
		return this.ingredientRepository.findAllByRecipeIdRecipe(idRecipe);
	}
	
	public Ingredient findIngredientById(Long idIngredient) {
		
		return this.ingredientRepository.findById(idIngredient).get();
	}
	
	public void saveIngredient(Ingredient ingredient) {
		
		this.ingredientRepository.save(ingredient);
	}
	
	public void deleteIngredient(Ingredient ingredient) {
		
		this.ingredientRepository.delete(ingredient);
	}
}
