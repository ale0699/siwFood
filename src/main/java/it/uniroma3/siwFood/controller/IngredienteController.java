package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.model.Ingrediente;
import it.uniroma3.siwFood.model.Ricetta;
import it.uniroma3.siwFood.service.IngredienteService;
import it.uniroma3.siwFood.service.RicettaService;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private RicettaService ricettaService;
	
	@GetMapping(value = "/formAddIngredientRecipe/{idRicetta}")
	public String getformAddIngredientRecipe(@PathVariable("idRicetta")Long idRicetta, Model model) {
		model.addAttribute(new Ingrediente());
		model.addAttribute("recipe", this.ricettaService.findRecipeById(idRicetta));
		model.addAttribute("ingredients", this.ingredienteService.findIngredientsByRicettaId(idRicetta));
		return "ingredients/formAddIngredientRecipe.html";
	}
	
	@PostMapping(value = "/addIngredient/{idRicetta}")
	public String postAddIngredientRecipe(@ModelAttribute Ingrediente ingrediente, @PathVariable("idRicetta")Long idRicetta) {
		Ricetta ricetta = this.ricettaService.findRecipeById(idRicetta);
		ingrediente.setRicetta(ricetta);
		this.ingredienteService.saveIngredient(ingrediente);
		return "redirect:/formAddIngredientRecipe/"+idRicetta;
	}
	
	@GetMapping(value = "/removeIngredient/{idIngrediente}/{idRicetta}")
	public String getRemoveIngredient(@PathVariable("idIngrediente")Long idIngrediente, @PathVariable("idRicetta")Long idRicetta, Model model) {
		Ingrediente ingrediente = this.ingredienteService.findIngredientById(idIngrediente);
		this.ingredienteService.deleteIngredient(ingrediente);
		model.addAttribute("recipe", this.ricettaService.findRecipeById(idRicetta));
		return "redirect:/recipeDetails/"+idRicetta;
	}
}
