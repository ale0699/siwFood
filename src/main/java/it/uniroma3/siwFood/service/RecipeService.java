package it.uniroma3.siwFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.repository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired 
	private CredentialsService credentialsService;
	
	@Autowired
	private CookService cookService;
	
	public boolean existsRecipeById(Long idRicetta) {
		return this.recipeRepository.existsById(idRicetta);
	}
	
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
	
	/*SE LA RICETTA GIÀ ESISTE VERIFICO CHE POSSA ESSER MODIFICATA (CUOCO O ADMIN)
	 * SE È UNA NUOVA RICETTA (idRecipe = NULL) LA AGGIUNGO*/
	public void saveRecipe(Recipe recipe) throws AccessDeniedException {
		
		if(recipe.getIdRecipe() != null) {
			
			if(this.existsRecipeById(recipe.getIdRecipe())) {
				
			    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
				Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
				
				if(recipe.getCook().equals(cook) || credentials.getRole().equals("ADMIN")) {
					this.recipeRepository.save(recipe);
				}
				else {
					throw new AccessDeniedException("You do not have permission to save this recipe");
				}
			}
		}
		else {
			
			this.recipeRepository.save(recipe);
		}
	}
	
	public void deleteRecipe(Recipe recipe) throws AccessDeniedException{
		
	    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		
		if(recipe.getCook().equals(cook) || credentials.getRole().equals("ADMIN")) {
			this.recipeRepository.delete(recipe);
		}
		else {
			throw new AccessDeniedException("You do not have permission to remove this recipe");
		}
	}
}
