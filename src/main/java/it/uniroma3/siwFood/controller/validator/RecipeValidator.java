package it.uniroma3.siwFood.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwFood.model.Recipe;
import it.uniroma3.siwFood.service.RecipeService;

@Component
public class RecipeValidator implements Validator {
	
	@Autowired
	private RecipeService recipeService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Recipe.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Recipe recipe = (Recipe) target;
		
		if (this.recipeService.existsByNameAndByCook(recipe.getName(),recipe.getCook().getIdCook())) {
			
			errors.reject("message.recipeDuplicate");
		}
		
	}

}
