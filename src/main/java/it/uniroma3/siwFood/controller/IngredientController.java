package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.model.Ingredient;
import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.service.IngredientService;
import it.uniroma3.siwFood.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private RecipeService recipeService;
	
	/*VIENE AGGIUNTO AL DATABASE UN NUOVO INGREDIENTE ASSOCIATO AD UNA RICETTA. SE CHI AGGIUNGE IL NUOVO
	 * INGREDIENTE, NON È AUTORIZZATO(OSSIA NON È IL PROPRIETARIO DELLA RICETTA OPPURE NON È UN ADMIN), 
	 * VIENE SOLLEVATA UN ECCEZIONE DI ERROR 403 FORBIDDEN */
	@PostMapping(value = {"/cook/addIngredient/{idRecipe}", "/admin/addIngredient/{idRecipe}"})
	public String postAddIngredientRecipe(@ModelAttribute Ingredient ingrediente, @PathVariable("idRecipe")Long idRecipe, HttpServletRequest request) {
		
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		ingrediente.setRecipe(recipe);
		try {
			this.ingredientService.saveIngredient(ingrediente);
			return "redirect:/cook/recipeManage/"+recipe.getIdRecipe();

		} catch (AccessDeniedException e) {
			throw new AccessDeniedException("You do not have permission to add ingredients");
		}
	}
	
	/*VIENE RIMOSSO DAL DATABASE UN INGREDIENTE ASSOCIATO AD UNA RICETTA. SE CHI RIMUOVE IL NUOVO
	 * INGREDIENTE, NON È AUTORIZZATO(OSSIA NON È IL PROPRIETARIO DELLA RICETTA OPPURE NON È UN ADMIN), 
	 * VIENE SOLLEVATA UN ECCEZIONE DI ERROR 403 FORBIDDEN */
	@GetMapping(value = {"/cook/removeIngredient/{idIngredient}/{idRecipe}", "/admin/removeIngredient/{idIngredient}/{idRecipe}"})
	public String getRemoveIngredient(@PathVariable("idIngredient")Long idIngredient, @PathVariable("idRecipe")Long idRecipe, Model model, HttpServletRequest request) {
		Ingredient ingredient = this.ingredientService.findIngredientById(idIngredient);
		
		try {
			this.ingredientService.deleteIngredient(ingredient);
			return "redirect:/cook/recipeManage/"+ingredient.getRecipe().getIdRecipe();
			
		} catch (AccessDeniedException e) {
			throw new AccessDeniedException("You do not have permission to remove ingredients");
		}
	}
}
