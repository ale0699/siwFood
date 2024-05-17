package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siwFood.service.CuocoService;
import it.uniroma3.siwFood.service.RicettaService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private CuocoService cuocoService;

    @GetMapping("/dashboard")
    public String getAdminDashboard(Model model) {
        model.addAttribute("totalRecipes", this.ricettaService.findAllRecipes().size());
        model.addAttribute("totalChefs", this.cuocoService.findAllCooks().size());
        return "admin/dashboard.html";
    }

}
