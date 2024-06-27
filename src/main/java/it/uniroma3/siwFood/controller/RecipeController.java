package it.uniroma3.siwFood.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.model.Ingredient;
import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.CredentialsService;
import it.uniroma3.siwFood.service.ImageService;
import it.uniroma3.siwFood.service.IngredientService;
import it.uniroma3.siwFood.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RecipeController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private ImageService imageService;
	
	/*TUTTE LE RICETTE PRESENTI NEL SISTEMA*/
	@GetMapping(value = "/recipes")
	public String getRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAllRecipes());
		return "recipes/recipes.html";
	}
	
	/*DETTAGLI DI UNA SINGOLA RICETTA CON UN CERTO ID, PER UTENTI NON AUTENTICATI*/
	@GetMapping(value = "/recipeDetails/{idRecipe}")
	public String getRecipeDetails(@PathVariable("idRecipe") Long idRecipe, Model model) {
		
        model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
        model.addAttribute("recipe", this.recipeService.findRecipeById(idRecipe));
	    return "recipes/recipeDetails.html";
	}
	
	/*FORM PER POTER AGGIUNGERE UNA RICETTA*/
	@GetMapping(value = {"/formAddRecipe", "/admin/formAddRecipe"})
	public String getFormAddRecipe(Model model) {
		model.addAttribute("cooks", this.cookService.findAllCooks());
		model.addAttribute("recipe", new Recipe());
		return "recipes/formAddRecipe.html";
	}
	
	/*METODO PER POTER SALVARE ALL'INTERNO DEL DATABASE UNA NUOVA RICETTA, GRAZIE AD UNA RICHIESTA HTTP.POST*/
	@PostMapping(value = {"/cook/addRecipe", "/admin/addRecipe"})
	public String postAddRecipe(@ModelAttribute Recipe recipe, @RequestParam(value = "idCook", required = false)Long idCook) throws IOException {
		
		Cook cook;
		
		//sta aggiungendo il cuoco l'admin dal form
		if(idCook!=null) {
			
			cook = this.cookService.findCookByIdCook(idCook);
		}
		else {
			
	    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
			cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		}

		recipe.setCook(cook);
		this.recipeService.saveRecipe(recipe); //può sollevare eccezioni
		return "redirect:/cook/recipeManage/"+recipe.getIdRecipe();
	}
	
	@PostMapping(value = {"/cook/addRecipeImage/{idRecipe}", "/admin/addRecipeImage/{idRecipe}"})
	public String postAddRecipeImage(@RequestParam("image-recipe")MultipartFile image, @PathVariable("idRecipe")Long idRecipe) throws IOException {
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		try { 
			
			String nameImage = this.imageService.saveImage(image, "src/main/resources/static/images/recipes");
			recipe.getPictureRecipe().add("/images/recipes/"+nameImage);
			this.recipeService.saveRecipe(recipe); //può sollevare eccezioni
		}
		catch (IOException e) {
			
			throw new IOException("Empty file");
		}
		
		return "redirect:/cook/recipeManage/"+recipe.getIdRecipe();
	}
	
	@GetMapping(value = "/cook/removeImage/{idRecipe}/{index}")
	public String postRemoveRecipeImage(@PathVariable("idRecipe")Long idRecipe, @PathVariable("index")int index) throws IOException {
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		String image = recipe.getPictureRecipe().get(index);
		try { 
			
			this.imageService.removeImage(image);
			recipe.getPictureRecipe().remove(index);
			this.recipeService.saveRecipe(recipe); //può sollevare eccezioni
		}
		catch (IOException e) {
			
			throw new IOException("Empty file");
		}
		
		return "redirect:/cook/recipeManage/"+recipe.getIdRecipe();
	}
	
	@GetMapping(value = "/cook/recipeManage/{idRecipe}")
	public String getRecipeManage(@PathVariable("idRecipe")Long idRecipe, Model model) {
		model.addAttribute("recipe", this.recipeService.findRecipeById(idRecipe));
		model.addAttribute(new Ingredient());
		model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
		return "recipes/recipeManage.html";
	}

	/*METODO PER POTER RIMUOVERE DAL DATABASE UNA RICETTA CHE HA COME ID idRecipe,
	 * SARÀ POSSIBILE RIMUOVERE SOLTANTO LE RICETTE CHE APPARTENGONO AD UN CUOCO.
	 * GLI ADMIN POSSONO ELIMINARE TUTTE LE RICETTE*/
	@GetMapping(value = {"/cook/removeRecipe/{idRecipe}", "/admin/removeRecipe/{idRecipe}"})
	public String getRemoveIngredient(@PathVariable("idRecipe")Long idRecipe, Model model, HttpServletRequest request) throws Exception {
		
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		
	    try {
	        this.recipeService.deleteRecipe(recipe);
			
	        return "redirect:/cook/dashboard";
		    
	    } catch (AccessDeniedException e) {
			throw new  AccessDeniedException("You do not have permission to remove this recipe");
	    }
	}

	/*METODO PER POTER MODIFICARE ALL'INTERNO DEL DATABASE UNA RICETTA, GRAZIE AD UNA RICHIESTA HTTP.POST*/
    @PostMapping(value = {"/cook/updateRecipe", "/admin/updateRecipe"})
    public String postUpdateRecipe(@ModelAttribute Recipe recipe) {

    	try {
            Recipe editedRecipe = this.recipeService.findRecipeById(recipe.getIdRecipe());
            editedRecipe.setName(recipe.getName());
            editedRecipe.setDescription(recipe.getDescription());
            this.recipeService.saveRecipe(editedRecipe);
	        return "redirect:/cook/recipeManage/"+recipe.getIdRecipe();
		} catch (AccessDeniedException e) {
			throw new AccessDeniedException("You do not have permission to edit this recipe");
		}
    }
	
	/*VENGONO VISUALIZZATE TUTTE LE RICETTE CHE HANNO UN INGREDIENTE CHE INIZIA CON UN CERTO NOME*/
	@GetMapping(value = "/recipesWithIngredient/{idIngredient}")
	public String getRecipesWithIngredient(@PathVariable("idIngredient")Long idIngredient, Model model) {
		Ingredient ingredient = this.ingredientService.findIngredientById(idIngredient);
		model.addAttribute("ingredient", ingredient.getName());
		model.addAttribute("recipes", this.recipeService.findRecipesByIngredientName(ingredient.getName()));
		return "recipes/recipesWithIngredient.html";
	}
	
	/*VENGONO VISUALIZZATE LE RICETTE CHE INZIANO CON UN CERTO NOME*/
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
