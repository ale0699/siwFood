package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.uniroma3.siwFood.service.RicettaService;

@Controller
public class RicettaController {
	
	@Autowired
	private RicettaService ricettaService;
}
