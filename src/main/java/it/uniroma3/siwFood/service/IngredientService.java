package it.uniroma3.siwFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.model.Ingredient;
import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.repository.IngredientRepository;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private CookService cookService;
	
	public List<Ingredient> findIngredientsByRecipeId(Long idRecipe){
		
		return this.ingredientRepository.findAllByRecipeIdRecipe(idRecipe);
	}
	
	public Ingredient findIngredientById(Long idIngredient) {
		
		return this.ingredientRepository.findById(idIngredient).get();
	}
	
	/*VIENE SALVATO UN NUOVO INGREDIENTE SOLO SE È RICHIESTO DAL CUOCO PROPRIETARIO DELLA RICETTA
	 * OPPURE DA UN ADMIN*/
	public void saveIngredient(Ingredient ingredient) throws AccessDeniedException {
		
		Recipe recipe = ingredient.getRecipe();
	    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		
		if(recipe.getCook().equals(cook) || credentials.getRole().equals("ADMIN")) {
			
			this.ingredientRepository.save(ingredient);
		}				
		else {
			throw new AccessDeniedException("You do not have permission to add ingredients");
		}
	}
	
	/*VIENE ELIMINATO UN INGREDIENTE, DA UNA RICETTA, SOLO SE LO RICHIEDE IL CUOCO PROPRIETARIO DELLA
	 * RICETTA OPPURE UN ADMIN*/
	public void deleteIngredient(Ingredient ingredient) throws AccessDeniedException {
		
		Recipe recipe = ingredient.getRecipe();
	    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		
		if(recipe.getCook().equals(cook) || credentials.getRole().equals("ADMIN")) {
			
			this.ingredientRepository.delete(ingredient);
		}				
		else {
			throw new AccessDeniedException("You do not have permission to delete ingredients");
		}
	}
}
