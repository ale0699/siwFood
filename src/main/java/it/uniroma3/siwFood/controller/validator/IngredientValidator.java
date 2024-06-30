package it.uniroma3.siwFood.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwFood.model.Ingredient;
import it.uniroma3.siwFood.service.IngredientService;

@Component
public class IngredientValidator implements Validator {
	
	@Autowired
	private IngredientService ingredientService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Ingredient.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ingredient ingredient = (Ingredient) target;
		
		if(this.ingredientService.existsByNameAndRecipe(ingredient.getName(), ingredient.getRecipe().getIdRecipe())) {
			
			errors.reject("message.ingredientDuplicate");
		}
	}

}
