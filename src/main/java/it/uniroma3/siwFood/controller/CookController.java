package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siwFood.model.Credentials;
import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.service.CredentialsService;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.RecipeService;

@Controller
public class CookController {

	@Autowired
	private CookService cookService;



}
