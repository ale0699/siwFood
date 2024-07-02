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
	
	public boolean existsByNameAndRecipe(String name, Long idRecipe) {
		
		return this.ingredientRepository.existsByNameIgnoreCaseAndRecipeIdRecipe(name, idRecipe);
	}
	
	public void saveIngredient(Ingredient ingredient) throws AccessDeniedException {
		
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credenziali = this.credentialsService.findCredenzialiByUsername(user.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credenziali.getIdCredentials());
		
		//se l'utente attuale è l'admin oppure il proprietario della ricetta, salvo l'ingrediente
		if( (cook==null && credenziali.getRole().equals("ADMIN")) ||  ingredient.getRecipe().getCook().equals(cook)) {
			
			this.ingredientRepository.save(ingredient);
		}
		else {
			
			throw new AccessDeniedException("Access Denied");
		}
		

	}
	
	public void deleteIngredient(Ingredient ingredient) throws AccessDeniedException {
		
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credenziali = this.credentialsService.findCredenzialiByUsername(user.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credenziali.getIdCredentials());
		
		//se l'utente attuale è l'admin oppure il proprietario della ricetta, elimino l'ingrediente
		if( (cook==null && credenziali.getRole().equals("ADMIN")) ||  ingredient.getRecipe().getCook().equals(cook)) {
			
			this.ingredientRepository.delete(ingredient);
		}
		else {
			
			throw new AccessDeniedException("Access Denied");
		}
	}

}
