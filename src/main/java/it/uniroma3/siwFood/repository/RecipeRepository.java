package it.uniroma3.siwFood.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	
	public List<Recipe> findAllByIngredientsNameContainsAllIgnoreCase(String ingredientName);
	
	public List<Recipe> findAllByNameContainsAllIgnoreCase(String recipeName);
	
	public List<Recipe> findAllByCookIdCook(Long idCook);
}
