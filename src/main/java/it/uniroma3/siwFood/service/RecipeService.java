package it.uniroma3.siwFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.repository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	public List<Recipe> findAllRecipes(){
		return (List<Recipe>) this.recipeRepository.findAll();
	}
	
	public Recipe findRecipeById(Long idRecipe){
		return this.recipeRepository.findById(idRecipe).get();
	}
	
	public List<Recipe> findRecipesByIngredientName(String ingredientName){
		return this.recipeRepository.findAllByIngredientsNameContainsAllIgnoreCase(ingredientName);
	}
	
	public List<Recipe> findRecipesByName(String name){
		return this.recipeRepository.findAllByNameContainsAllIgnoreCase(name);
	}
	
	public List<Recipe> findRecipesByCookId(Long idCook){
		return this.recipeRepository.findAllByCookIdCook(idCook);
	}
	
	public void saveRecipe(Recipe recipe) {
		this.recipeRepository.save(recipe);
	}
	
	public void deleteRecipe(Recipe recipe) {
		this.recipeRepository.delete(recipe);
	}

}
