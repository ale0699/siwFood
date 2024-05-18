package it.uniroma3.siwFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.RecipeService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CookService cookService;

    @GetMapping("/dashboard")
    public String getAdminDashboard(Model model) {
        model.addAttribute("totalRecipes", this.recipeService.findAllRecipes().size());
        model.addAttribute("totalChefs", this.cookService.findAllCooks().size());
        return "admin/dashboard.html";
    }

}
