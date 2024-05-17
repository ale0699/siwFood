package it.uniroma3.siwFood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Credenziali;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
	
	public Credenziali findByUsername(String username);
}
