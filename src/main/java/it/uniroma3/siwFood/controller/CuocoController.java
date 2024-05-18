package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.service.CredentialsService;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.RecipeService;

@Controller
@RequestMapping("/cook")
public class CuocoController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping("/dashboard")
	public String getDashboardCook(Model model) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		model.addAttribute("cook", cook);
		model.addAttribute("recipes", this.recipeService.findRecipesByCookId(cook.getIdCook()));
		return "cooks/dashboard.html";
	}
	
	@GetMapping("/recipes")
	public String getRecipesCook(Model model){
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		model.addAttribute("recipes", this.recipeService.findRecipesByCookId(cook.getIdCook()));
		return "recipes/recipes.html";
	}
	

}
