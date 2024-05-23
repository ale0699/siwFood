package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.service.CookService;

@Controller
public class CookController {

	@Autowired
	private CookService cookService;

	@GetMapping(value = "/admin/formAddCook")
	public String getFormAddCook(Model model) {
		model.addAttribute("cook", new Cook());
		return "cooks/formAddCook.html";
	}
	
	@PostMapping(value = "/admin/addCook")
	public String getAddCook(@ModelAttribute Cook cook, Model model) {
		this.cookService.saveCook(cook);
		return "redirect:cooks";
	}

}
