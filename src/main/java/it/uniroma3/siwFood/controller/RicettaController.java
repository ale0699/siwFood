package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.model.Ricetta;
import it.uniroma3.siwFood.service.RicettaService;

@Controller
public class RicettaController {
	
	@Autowired
	private RicettaService ricettaService;
	
	@GetMapping(value = "/recipes")
	public String getRecipes(Model model) {
		model.addAttribute("recipes", this.ricettaService.findAllRecipes());
		return "ricette/recipes.html";
	}
	
	@GetMapping(value = "/recipeDetails/{idRicetta}")
	public String getRecipeDetails(@PathVariable("idRicetta")Long idRicetta, Model model) {
		model.addAttribute("recipe", this.ricettaService.findRecipeById(idRicetta));
		return "ricette/recipeDetails.html";
	}
	
	@GetMapping(value = "/formAddRecipe")
	public String getFormAddRecipe(Model model) {
		model.addAttribute(new Ricetta());
		return "ricette/formAddRecipe.html";
	}
	
	@PostMapping(value = "/addRecipe")
	public String postAddRecipe(@ModelAttribute Ricetta ricetta) {
		this.ricettaService.saveRecipe(ricetta);
		return "redirect:/recipeDetails/"+ricetta.getIdRicetta();
	}
}
