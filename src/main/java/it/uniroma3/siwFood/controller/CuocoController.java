package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siwFood.model.Credenziali;
import it.uniroma3.siwFood.model.Cuoco;
import it.uniroma3.siwFood.service.CredenzialiService;
import it.uniroma3.siwFood.service.CuocoService;
import it.uniroma3.siwFood.service.RicettaService;

@Controller
public class CuocoController {
	
	@Autowired
	private CredenzialiService credenzialiService;
	
	@Autowired
	private CuocoService cuocoService;
	
	@Autowired
	private RicettaService ricettaService;
	
	@GetMapping("/cook/dashboard")
	public String getDashboardCook(Model model) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = this.credenzialiService.findCredenzialiByUsername(userDetails.getUsername());
		Cuoco cuoco = this.cuocoService.findCookByCredenziali(credenziali.getIdCredenziali());
		model.addAttribute("cook", cuoco);
		model.addAttribute("recipes", this.ricettaService.findRecipesByCuocoId(cuoco.getIdCuoco()));
		return "cooks/dashboard.html";
	}
	
	@GetMapping("/cook/recipes")
	public String getRecipesCook(Model model){
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = this.credenzialiService.findCredenzialiByUsername(userDetails.getUsername());
		Cuoco cuoco = this.cuocoService.findCookByCredenziali(credenziali.getIdCredenziali());
		model.addAttribute("recipes", this.ricettaService.findRecipesByCuocoId(cuoco.getIdCuoco()));
		return "recipes/recipes.html";
	}
	

}
