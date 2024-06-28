package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.CredentialsService;
import it.uniroma3.siwFood.service.RecipeService;

@Controller
public class IndexController {
	
    @Autowired
    private RecipeService recipeService;
    
	@Autowired
	private CredentialsService credentialsService;

    @Autowired
    private CookService cookService;
	
	@GetMapping(value = "/")
	public String getIndexPage() {
		return "index.html";
	}
	
    @GetMapping(value = "/admin/dashboard")
    public String getAdminDashboard(Model model) {
        model.addAttribute("cooks", this.cookService.findAllCooks());
        return "admin/dashboard.html";
    }
    
	@GetMapping(value = "/cook/dashboard")
	public String getDashboardCook(Model model) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = this.credentialsService.findCredenzialiByUsername(userDetails.getUsername());
		Cook cook = this.cookService.findCookByCredentials(credentials.getIdCredentials());
        model.addAttribute("totalRecipes", this.recipeService.findRecipesByCookId(cook.getIdCook()).size());
        model.addAttribute("recipesCook", this.recipeService.findRecipesByCookId(cook.getIdCook()));
		model.addAttribute("cook", cook);
		return "cooks/dashboard.html";
	}
}
