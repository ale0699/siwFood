package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping(value = "/formAddIngredientRecipe/{idRecipe}")
	public String getformAddIngredientRecipe(@PathVariable("idRecipe")Long idRecipe, Model model) {
		model.addAttribute("ingredient", new Ingredient());
		model.addAttribute("recipe", this.recipeService.findRecipeById(idRecipe));
		model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
		return "ingredients/formAddIngredientRecipe.html";
	}
	
	@PostMapping(value = "/addIngredient/{idRecipe}")
	public String postAddIngredientRecipe(@ModelAttribute Ingredient ingrediente, @PathVariable("idRecipe")Long idRecipe) {
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		ingrediente.setRecipe(recipe);
		this.ingredientService.saveIngredient(ingrediente);
		return "redirect:/formAddIngredientRecipe/"+idRecipe;
	}
	
	@GetMapping(value = "/removeIngredient/{idIngredient}/{idRecipe}")
	public String getRemoveIngredient(@PathVariable("idIngredient")Long idIngredient, @PathVariable("idRecipe")Long idRecipe, Model model) {
		Ingredient ingredient = this.ingredientService.findIngredientById(idIngredient);
		this.ingredientService.deleteIngredient(ingredient);
		model.addAttribute("recipe", this.recipeService.findRecipeById(idRecipe));
		return "redirect:/recipeDetails/"+idRecipe;
	}
}
