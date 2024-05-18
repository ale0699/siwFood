package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.service.CookService;

@Controller
public class AuthController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CookService cookService;
	
	@GetMapping(value = "/login")
	public String getLoginPage() {
		
		return "login.html";
	}
	
	@GetMapping(value = "/register")
	public String getRegisterCook(Model model){
		model.addAttribute("cook", new Cook());
		return "register.html";
	}
	
	@PostMapping(value = "/register")
	public String postRegisterCook(@ModelAttribute Cook cook){
		Credentials credentials = cook.getCredentials();
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		credentials.setRole("COOK");
		this.cookService.saveCook(cook);
		return "redirect:/login";
	}
	
	@GetMapping(value = "/login/error")
	public String getLoginErrorPage(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

}
