package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwFood.model.Credenziali;
import it.uniroma3.siwFood.model.Cuoco;
import it.uniroma3.siwFood.service.CuocoService;

@Controller
public class AuthController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CuocoService cuocoService;
	
	@GetMapping(value = "/login")
	public String getLoginPage() {
		
		return "login.html";
	}
	
	@GetMapping(value = "/register")
	public String getRegisterCook(Model model){
		model.addAttribute(new Cuoco());
		return "register.html";
	}
	
	@PostMapping(value = "/register")
	public String postRegisterCook(@ModelAttribute Cuoco cuoco){
		Credenziali credenziali = cuoco.getCredenziali();
		credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
		credenziali.setRuolo("CUOCO");
		this.cuocoService.saveCook(cuoco);
		return "redirect:/login";
	}
	
	@GetMapping(value = "/login/error")
	public String getLoginErrorPage(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

}
