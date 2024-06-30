package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.controller.validator.IngredientValidator;
import it.uniroma3.siwFood.model.Ingredient;
import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.service.IngredientService;
import it.uniroma3.siwFood.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private IngredientValidator ingredientValidator;
	
	/*VIENE AGGIUNTO AL DATABASE UN NUOVO INGREDIENTE ASSOCIATO AD UNA RICETTA. SE CHI AGGIUNGE IL NUOVO
	 * INGREDIENTE, NON È AUTORIZZATO(OSSIA NON È IL PROPRIETARIO DELLA RICETTA OPPURE NON È UN ADMIN), 
	 * VIENE SOLLEVATA UN ECCEZIONE DI ERROR 403 FORBIDDEN */
	@PostMapping(value = "/cook/ingredients/add/recipes/{idRecipe}")
	public String postAddIngredientRecipe(@Valid @ModelAttribute Ingredient ingrediente, @PathVariable("idRecipe")Long idRecipe, BindingResult bindingResult, Model model) {
		
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		ingrediente.setRecipe(recipe);
		
		this.ingredientValidator.validate(ingrediente, bindingResult);
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("recipe", this.recipeService.findRecipeById(idRecipe));
			model.addAttribute("ingredient", ingrediente);
			model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
			return "recipes/recipeManage.html";
		}
		
		try {
			this.ingredientService.saveIngredient(ingrediente);
			return "redirect:/cook/recipes/edit/"+recipe.getIdRecipe();

		} catch (AccessDeniedException e) {
			throw new AccessDeniedException("You do not have permission to add ingredients");
		}
	}
	
	/*VIENE RIMOSSO DAL DATABASE UN INGREDIENTE ASSOCIATO AD UNA RICETTA. SE CHI RIMUOVE IL NUOVO
	 * INGREDIENTE, NON È AUTORIZZATO(OSSIA NON È IL PROPRIETARIO DELLA RICETTA OPPURE NON È UN ADMIN), 
	 * VIENE SOLLEVATA UN ECCEZIONE DI ERROR 403 FORBIDDEN */
	@GetMapping(value = "/cook/ingredients/remove/{idIngredient}/recipes/{idRecipe}")
	public String getRemoveIngredient(@PathVariable("idIngredient")Long idIngredient, @PathVariable("idRecipe")Long idRecipe, Model model, HttpServletRequest request) {
		Ingredient ingredient = this.ingredientService.findIngredientById(idIngredient);
		
		try {
			this.ingredientService.deleteIngredient(ingredient);
			return "redirect:/cook/recipes/edit/"+ingredient.getRecipe().getIdRecipe();
			
		} catch (AccessDeniedException e) {
			throw new AccessDeniedException("You do not have permission to remove ingredients");
		}
	}
}
