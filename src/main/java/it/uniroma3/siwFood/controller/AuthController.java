package it.uniroma3.siwFood.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwFood.controller.validator.CookValidator;
import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.ImageService;
import jakarta.validation.Valid;

@Controller
public class AuthController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private CookValidator cookValidator;
	
	@Autowired 
	private ImageService imageService;
	
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
	public String postRegisterCook(@RequestParam("image-cook") MultipartFile pictureFile, @Valid @ModelAttribute("cook") Cook cook, BindingResult bindingResult) throws IOException{		

		try {
			String nameImage = this.imageService.saveImage(pictureFile, "src/main/resources/static/images/cooks/");
			if(nameImage != null) {
				
				cook.setPicture("/images/cooks/"+nameImage);
			}
		}
		catch (IOException e) {
			
			throw new IOException("Empty file");
		}

		Credentials credentials = cook.getCredentials();
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		credentials.setRole("COOK");
		
		this.cookValidator.validate(cook, bindingResult);

		if(!bindingResult.hasErrors()) {
			
			this.cookService.saveCook(cook);
			return "redirect:/login";
		}
		else {
			
			return "register.html";
		}
	}
	
	@GetMapping(value = "/login/error")
	public String getLoginErrorPage(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

}
