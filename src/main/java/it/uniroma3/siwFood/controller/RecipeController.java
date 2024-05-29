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
	@GetMapping(value = {"/recipes", "/admin/recipes"})
	public String getRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAllRecipes());
		return "recipes/recipes.html";
	}
	
	/*TUTTE LE RICETTE CONDIVISE DA UN DETERMINATO CUOCO*/
	@GetMapping(value = "/cook/recipes")
	public String getRecipesCook(Model model){
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		model.addAttribute("recipes", this.recipeService.findRecipesByCookId(cook.getIdCook()));
		return "recipes/recipes.html";
	}
	
	/*DETTAGLI DI UNA SINGOLA RICETTA CON UN CERTO ID, PER UTENTI NON AUTENTICATI*/
	@GetMapping(value = "/recipeDetails/{idRecipe}")
	public String getRecipeDetails(@PathVariable("idRecipe") Long idRecipe, Model model) {
		
        model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
        model.addAttribute("recipe", this.recipeService.findRecipeById(idRecipe));
	    return "recipes/recipeDetailsUnauthenticated.html";
	}
	
	/*DETTAGLI DI UNA SINGOLA RICETTA CON UN CERTO ID, VISUALIZZABILE SOLO DAL
	 * CUOCO CHE L'HA CONDIVISA O DA UN ADMIN. IN CASO CONTRARIO VIENE SOLLEVATA UN ECCEZIONE*/
	@GetMapping(value = {"/cook/recipeDetails/{idRecipe}", "/admin/recipeDetails/{idRecipe}"})
	public String getRecipeDetailsAuthenticated(@PathVariable("idRecipe") Long idRecipe, Model model, HttpServletRequest request) {
		
	    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		if(recipe.getCook().equals(cook) || credentials.getRole().equals("ADMIN")) {
			//mi serve nel template per poter indirizzare la form nel giusto url di post per l'aggiornamento della ricetta
		    String referer = request.getRequestURI(); // Ottieni l'URI della richiesta
		    if (referer.startsWith("/admin")) {
				model.addAttribute("backUrl", "/admin");
		    }
		    else {
				model.addAttribute("backUrl", "/cook");
		    }
	        model.addAttribute("ingredients", this.ingredientService.findIngredientsByRecipeId(idRecipe));
	        model.addAttribute("recipe", recipe);
			return "recipes/recipeDetails.html";
		}
		
		//solleva un eccezione --> codice 403 Forbidden
	    throw new AccessDeniedException("You do not have permission to edit this recipe");
	}

	/*FORM PER POTER AGGIUNGERE UNA RICETTA*/
	@GetMapping(value = {"/cook/formAddRecipe", "/admin/formAddRecipe"})
	public String getFormAddRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "recipes/formAddRecipe.html";
	}
	
	/*METODO PER POTER SALVARE ALL'INTERNO DEL DATABASE UNA NUOVA RICETTA, GRAZIE AD UNA RICHIESTA HTTP.POST*/
	@PostMapping(value = {"/cook/addRecipe", "/admin/addRecipe"})
	public String postAddRecipe(@RequestParam("image-recipe")MultipartFile image, @ModelAttribute Recipe recipe) throws IOException {
		
		try { 
			
			String nameImage = this.imageService.saveImage(image, "src/main/resources/static/images/recipes");
			recipe.getPictureRecipe().add("/images/recipes/"+nameImage);
		}
		catch (IOException e) {
			
			throw new IOException("Empty file");
		}
		
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
		recipe.setCook(cook);
		this.recipeService.saveRecipe(recipe);
		return "redirect:recipeDetails/"+recipe.getIdRecipe();
	}
	
	/*METODO PER POTER RIMUOVERE DAL DATABASE UNA RICETTA CHE HA COME ID idRecipe,
	 * SARÀ POSSIBILE RIMUOVERE SOLTANTO LE RICETTE CHE APPARTENGONO AD UN CUOCO.
	 * GLI ADMIN POSSONO ELIMINARE TUTTE LE RICETTE*/
	@GetMapping(value = {"/cook/removeRecipe/{idRecipe}", "/admin/removeRecipe/{idRecipe}"})
	public String getRemoveIngredient(@PathVariable("idRecipe")Long idRecipe, Model model, HttpServletRequest request) throws Exception {
		
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		
	    try {
	        this.recipeService.deleteRecipe(recipe);
			
			model.addAttribute("recipes", this.recipeService.findAllRecipes());
			
		    // Ottieni il referer per determinare l'URL di post-rimozione
		    String referer = request.getRequestURI(); // Ottieni l'URI della richiesta
		    
		    if (referer.startsWith("/admin")) {
		        return "redirect:/admin/recipes";
		    }
		    return "redirect:/cook/recipes";
		    
	    } catch (AccessDeniedException e) {
			throw new  AccessDeniedException("You do not have permission to remove this recipe");
	    }
	}
	
	/*FORM PER POTER MODIFICARE UNA RICETTA*/
    @GetMapping(value = {"/cook/formEditRecipe/{idRecipe}", "/admin/formEditRecipe/{idRecipe}"})
    public String getFormEditRecipe(@PathVariable("idRecipe") Long idRecipe, Model model, HttpServletRequest request) {
		Recipe recipe = this.recipeService.findRecipeById(idRecipe);
		model.addAttribute("recipe", recipe);
		
		//mi serve nel template per poter indirizzare la form nel giusto url di post per l'aggiornamento della ricetta
	    String referer = request.getRequestURI(); // Ottieni l'URI della richiesta
	    if (referer.startsWith("/admin")) {
			model.addAttribute("backUrl", "/admin");
	    }
	    else {
			model.addAttribute("backUrl", "/cook");
	    }
		return "recipes/formEditRecipe.html";
    }

	/*METODO PER POTER MODIFICARE ALL'INTERNO DEL DATABASE UNA RICETTA, GRAZIE AD UNA RICHIESTA HTTP.POST*/
    @PostMapping(value = {"/cook/updateRecipe", "/admin/updateRecipe"})
    public String postUpdateRecipe(@ModelAttribute Recipe recipe) {
    	
    	try {
            Recipe editedRecipe = this.recipeService.findRecipeById(recipe.getIdRecipe());
            editedRecipe.setName(recipe.getName());
            editedRecipe.setDescription(recipe.getDescription());
            this.recipeService.saveRecipe(editedRecipe);
            return "redirect:recipeDetails/" + recipe.getIdRecipe();
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
