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
	
	public boolean existsByNameAndByCook(String name, Long idCook) {
		
		return this.recipeRepository.existsByNameAllIgnoreCaseAndCookIdCook(name,idCook);
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
	
	public void saveRecipe(Recipe recipe) {
		
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credenziali = this.credentialsService.findCredenzialiByUsername(user.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credenziali.getIdCredentials());
		
		//se l'utente attuale è l'admin oppure il proprietario della ricetta, salvo la ricetta
		if( (cook==null && credenziali.getRole().equals("ADMIN")) ||  recipe.getCook().equals(cook)) {
			
			this.recipeRepository.save(recipe);
		}
		else {
			
			throw new AccessDeniedException("Access Denied");
		}

	}
	
	public void deleteRecipe(Recipe recipe) {
		
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credenziali = this.credentialsService.findCredenzialiByUsername(user.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credenziali.getIdCredentials());
		
		//se l'utente attuale è l'admin oppure il proprietario della ricetta, elimino la ricetta
		if( (cook==null && credenziali.getRole().equals("ADMIN")) ||  recipe.getCook().equals(cook)) {
			
			this.recipeRepository.delete(recipe);
		}
		else {
			
			throw new AccessDeniedException("Access Denied");
		}
	}
}
