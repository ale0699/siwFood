package it.uniroma3.siwFood.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
	
	public List<Ingredient> findAllByRecipeIdRecipe(Long idRecipe);
	
	public boolean existsByNameIgnoreCaseAndRecipeIdRecipe(String name, Long idRecipe);
}
