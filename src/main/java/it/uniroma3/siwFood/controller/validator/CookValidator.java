package it.uniroma3.siwFood.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwFood.model.Cook;
import it.uniroma3.siwFood.service.CookService;
import it.uniroma3.siwFood.service.CredentialsService;

@Component
public class CookValidator implements Validator{
	
	@Autowired
	private CredentialsService credentialService;
	
	@Autowired
	private CookService cookService;

	@Override
	public boolean supports(Class<?> clazz) {

		return Cook.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Cook cook = (Cook) target;

		if(cook.getCredentials() != null && this.credentialService.existsByUsername(cook.getCredentials().getUsername())) {
			
			errors.reject("message.usernameDuplicate");
		}
		
		if(this.cookService.existsByNameAndSurnameAndDateBirth(cook.getName(), cook.getSurname(), cook.getDateBirth())) {
			
			errors.reject("message.cookDuplicate");
		}
	}
	
	
}
