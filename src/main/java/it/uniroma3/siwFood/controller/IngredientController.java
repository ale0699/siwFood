package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.model.Ingredient;
import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.CredentialsService;
import it.uniroma3.siwFood.service.IngredientService;
import it.uniroma3.siwFood.service.RecipeService;

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private CookService cookService;
	
	/*PUÒ AGGIUNGERE UN NUOVO INGREDIENTE ALLA RICETTA SOLO IL CUOCO CHE L'HA CONDIVISA OPPURE UN ADMIN.
	 * SENNÒ VIENE SOLLEVATA UN ECCEZIONE*/
	@GetMapping(value = {"/cook/formAddIngredientRecipe/{idRecipe}", "/admin/formAddIngredientRecipe/{idRecipe}"})
	public String getformAddIngredientRecipe(@PathVariable("idRecipe")Long idRecipe, Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		
		if(cook != null) {
			
			if(cook.equals(recipe.getCook()) ) {
				
				model.addAttribute("ingredient", new Ingredient());
				model.addAttribute("recipe", recipe);
				model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
				return "ingredients/formAddIngredientRecipe.html";
			}
			else {
				
				throw new AccessDeniedException("You do not have permission to add ingredient to this recipe");
			}
		}
		else {
			
			model.addAttribute("ingredient", new Ingredient());
			model.addAttribute("recipe", recipe);
			model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
			return "ingredients/formAddIngredientRecipe.html";
		}
	}
	
	@PostMapping(value = {"/cook/addIngredient/{idRecipe}", "/admin/addIngredient/{idRecipe}"})
	public String postAddIngredientRecipe(@ModelAttribute Ingredient ingrediente, @PathVariable("idRecipe")Long idRecipe) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		ingrediente.setRecipe(recipe);
		this.ingredientService.saveIngredient(ingrediente);
		if(credentials.getRole().equals("ADMIN")) {
			
			return "redirect:/admin/formAddIngredientRecipe/"+idRecipe;
		}
		return "redirect:/cook/formAddIngredientRecipe/"+idRecipe;
	}
	
	@GetMapping(value = {"/cook/removeIngredient/{idIngredient}/{idRecipe}", "/admin/removeIngredient/{idIngredient}/{idRecipe}"})
	public String getRemoveIngredient(@PathVariable("idIngredient")Long idIngredient, @PathVariable("idRecipe")Long idRecipe, Model model) {
		Ingredient ingredient = this.ingredientService.findIngredientById(idIngredient);
		this.ingredientService.deleteIngredient(ingredient);
		model.addAttribute("recipe", this.recipeService.findRecipeById(idRecipe));
		return "redirect:/recipeDetails/"+idRecipe;
	}
}
