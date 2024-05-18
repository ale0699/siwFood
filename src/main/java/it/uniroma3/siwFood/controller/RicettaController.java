package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Ingredient;
import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.service.CredentialsService;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.IngredientService;
import it.uniroma3.siwFood.service.RecipeService;

@Controller
public class RicettaController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private CookService cookService;
	
	@GetMapping(value = "/recipes")
	public String getRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAllRecipes());
		return "recipes/recipes.html";
	}
	
	@GetMapping(value = "/recipeDetails/{idRecipe}")
	public String getRecipeDetails(@PathVariable("idRecipe")Long idRecipe, Model model) {
		model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
		model.addAttribute("recipe", this.recipeService.findRecipeById(idRecipe));
		return "recipes/recipeDetails.html";
	}
	
	@GetMapping(value = "/formAddRecipe")
	public String getFormAddRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "recipes/formAddRecipe.html";
	}
	
	@PostMapping(value = "/addRecipe")
	public String postAddRecipe(@ModelAttribute Recipe recipe) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		recipe.setCook(cook);
		this.recipeService.saveRecipe(recipe);
		return "redirect:/recipeDetails/"+recipe.getIdRecipe();
	}
	
	@GetMapping(value = "/removeRecipe/{idRecipe}")
	public String getRemoveIngredient(@PathVariable("idRecipe")Long idRecipe, Model model) {
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		this.recipeService.deleteRecipe(recipe);
		model.addAttribute("recipes", this.recipeService.findAllRecipes());
		return "redirect:/recipes";
	}
	
    @GetMapping(value = "/formEditRecipe/{idRecipe}")
    public String getFormEditRecipe(@PathVariable("idRecipe") Long idRecipe, Model model) {
        Recipe recipe = this.recipeService.findRecipeById(idRecipe);
        model.addAttribute("recipe", recipe);
        return "recipes/formEditRecipe.html";
    }

    @PostMapping(value = "/updateRecipe")
    public String postUpdateRecipe(@ModelAttribute Recipe recipe) {

    	//va gestita meglio con existBy
		Recipe editedRecipe = this.recipeService.findRecipeById(recipe.getIdRecipe());
		editedRecipe.setName(recipe.getName());
		editedRecipe.setDescription(recipe.getDescription());
		editedRecipe.setCook(recipe.getCook());
        this.recipeService.saveRecipe(editedRecipe);
    	
        return "redirect:/recipeDetails/" + recipe.getIdRecipe();
    }
	
	@GetMapping(value = "/recipesWithIngredient/{idIngredient}")
	public String getRecipesWithIngredient(@PathVariable("idIngredient")Long idIngredient, Model model) {
		Ingredient ingredient = this.ingredientService.findIngredientById(idIngredient);
		model.addAttribute("ingredient", ingredient.getName());
		model.addAttribute("recipes", this.recipeService.findRecipesByIngredientName(ingredient.getName()));
		return "recipes/recipesWithIngredient.html";
	}
	
	@GetMapping(value = "/searchRecipes")
	public String getSearchRecipes(@RequestParam("nameRecipe")String nameRecipe, Model model) {
		model.addAttribute("recipes", this.recipeService.findRecipesByName(nameRecipe));
		return "recipes/recipes.html";
	}
	
	@GetMapping(value = "/searchRecipesByIngredient")
	public String getSearchRecipesByIngredient(@RequestParam("nameIngredient")String ingredientName, Model model) {
		model.addAttribute("ingredient", ingredientName);
		model.addAttribute("recipes", this.recipeService.findRecipesByIngredientName(ingredientName));
		return "recipes/recipesWithIngredient.html";
	}
}
