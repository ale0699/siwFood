package it.uniroma3.siwFood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.RecipeService;

@Component
@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private CookService cookService;
	
	@GetMapping(value = "/rest/recipes")
	public List<Recipe> getRecipes(){
		
		return this.recipeService.findAllRecipes();
	}
	
	@GetMapping(value = "/rest/recipes/{idRecipe}")
	public Recipe getRecipesIdRecipe(@PathVariable("idRecipe")Long idRecipe){
		
		return this.recipeService.findRecipeById(idRecipe);
	}
	
	@GetMapping(value = "/rest/cooks")
	public List<Cook> getCooks(){
		
		return this.cookService.findAllCooks();
	}
	
	@GetMapping(value = "/rest/cooks/{idCook}")
	public Cook getCooksIdCook(@PathVariable("idCook")Long idCook){
		
		return this.cookService.findCookByIdCook(idCook);
	}
}
