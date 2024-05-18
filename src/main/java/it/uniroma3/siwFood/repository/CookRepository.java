package it.uniroma3.siwFood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Cook;

public interface CookRepository extends CrudRepository<Cook, Long> {
	
	public Cook findByCredentialsIdCredentials(Long idCredentials);
}
