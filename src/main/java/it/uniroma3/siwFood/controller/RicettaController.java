package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwFood.model.Ingrediente;
import it.uniroma3.siwFood.model.Ricetta;
import it.uniroma3.siwFood.service.IngredienteService;
import it.uniroma3.siwFood.service.RicettaService;

@Controller
public class RicettaController {
	
	@Autowired
	private RicettaService ricettaService;
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@GetMapping(value = "/recipes")
	public String getRecipes(Model model) {
		model.addAttribute("recipes", this.ricettaService.findAllRecipes());
		return "recipes/recipes.html";
	}
	
	@GetMapping(value = "/recipeDetails/{idRicetta}")
	public String getRecipeDetails(@PathVariable("idRicetta")Long idRicetta, Model model) {
		model.addAttribute("ingredients", this.ingredienteService.findIngredientsByRicettaId(idRicetta));
		model.addAttribute("recipe", this.ricettaService.findRecipeById(idRicetta));
		return "recipes/recipeDetails.html";
	}
	
	@GetMapping(value = "/formAddRecipe")
	public String getFormAddRecipe(Model model) {
		model.addAttribute(new Ricetta());
		return "recipes/formAddRecipe.html";
	}
	
	@PostMapping(value = "/addRecipe")
	public String postAddRecipe(@ModelAttribute Ricetta ricetta) {
		this.ricettaService.saveRecipe(ricetta);
		return "redirect:/recipeDetails/"+ricetta.getIdRicetta();
	}
	
	@GetMapping(value = "/removeRecipe/{idRicetta}")
	public String getRemoveIngredient(@PathVariable("idRicetta")Long idRicetta, Model model) {
		Ricetta ricetta = this.ricettaService.findRecipeById(idRicetta);
		this.ricettaService.deleteRecipe(ricetta);
		model.addAttribute("recipes", this.ricettaService.findAllRecipes());
		return "redirect:/recipes";
	}
	
	@GetMapping(value = "/recipesWithIngredient/{idIngrediente}")
	public String getRecipesWithIngredient(@PathVariable("idIngrediente")Long idIngrediente, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findIngredientById(idIngrediente);
		model.addAttribute("ingredient", ingrediente);
		model.addAttribute("recipes", this.ricettaService.findRecipesByIngredienteNome(ingrediente.getNome()));
		return "recipes/recipesWithIngredient.html";
	}
	
	@GetMapping(value = "/searchRecipes")
	public String getSearchRecipes(@RequestParam("nameRecipe")String nameRecipe, Model model) {
		model.addAttribute("recipes", this.ricettaService.findRecipesByNome(nameRecipe));
		return "recipes/recipes.html";
	}
	
	@GetMapping(value = "/searchRecipesByIngredient")
	public String getSearchRecipesByIngredient(@RequestParam("nameIngredient")String nameIngredient, Model model) {
		model.addAttribute("ingredientSearched", nameIngredient);
		model.addAttribute("recipes", this.ricettaService.findRecipesByIngredienteNome(nameIngredient));
		return "recipes/recipes.html";
	}
}
