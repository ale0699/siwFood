package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.service.CookService;

@Controller
public class CookController {

	@Autowired
	private CookService cookService;
	
	@GetMapping(value = {"/cooks", "/cook/cooks", "/admin/cooks"})
	public String getCooks(Model model) {
		model.addAttribute("cooks", this.cookService.findAllCooks());
		return "cooks/cooks.html";
	}

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
	
	@GetMapping(value = "/admin/removeCook/{idCook}")
	public String getRemoveCook(@PathVariable("idCook")Long idCook ,Model model) {
		Cook cook = this.cookService.findCookByIdCook(idCook);
		this.cookService.removeCook(cook);
		return "redirect:cooks";
	}

}
