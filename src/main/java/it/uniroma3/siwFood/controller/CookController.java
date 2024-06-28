package it.uniroma3.siwFood.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.ImageService;
import it.uniroma3.siwFood.service.RecipeService;

@Controller
public class CookController {
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping(value = "/cooks")
	public String getCooks(Model model) {
		model.addAttribute("cooks", this.cookService.findAllCooks());
		return "cooks/cooks.html";
	}
	
	@GetMapping(value = "/cooks/{idCook}")
	public String getCookDetails(@PathVariable("idCook")Long idCook, Model model) {
		model.addAttribute("recipes", this.recipeService.findRecipesByCookId(idCook));
		model.addAttribute("cook", this.cookService.findCookByIdCook(idCook));
		return "cooks/cookDetails.html";	
	}

	@GetMapping(value = "/admin/cooks/formAdd")
	public String getFormAddCook(Model model) {
		model.addAttribute("cook", new Cook());
		return "cooks/formAddCook.html";
	}
	
	@PostMapping(value = "/admin/cooks/add")
	public String getAddCook(@RequestParam("image-cook")MultipartFile image, @ModelAttribute Cook cook, Model model) throws IOException {
	    
		String nameImage = this.imageService.saveImage(image, "src/main/resources/static/images/cooks");
		cook.setPicture(("/images/cooks/"+nameImage));
	    // Salva il cook nel database
	    this.cookService.saveCook(cook);
	    return "redirect:/admin/dashboard";
	}
	
	@GetMapping(value = "/admin/cooks/edit/{idCook}")
	public String getFormEditCook(@PathVariable("idCook")Long idCook, Model model) {
		Cook cook = this.cookService.findCookByIdCook(idCook);
		model.addAttribute("cook", cook);
		return "cooks/cookManage.html";
	}
	
	@PostMapping(value = "/admin/cooks/edit")
	public String postEditCook(@ModelAttribute Cook cook, Model model) throws IOException {
	    
		cook.setName(cook.getName());
		cook.setSurname(cook.getSurname());
		cook.setDateBirth(cook.getDateBirth());
	    this.cookService.saveCook(cook);
	    return "redirect:/admin/dashboard";
	}

	@GetMapping(value = "/admin/cooks/remove/{idCook}")
	public String getRemoveCook(@PathVariable("idCook")Long idCook ,Model model) throws IOException {
		Cook cook = this.cookService.findCookByIdCook(idCook);
		this.imageService.removeImage(cook.getPicture());
		this.cookService.removeCook(cook);
		return "redirect:/admin/dashboard";
	}

}
