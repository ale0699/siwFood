package it.uniroma3.siwFood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	
	public Credentials findByUsername(String username);
	
	public boolean existsByUsername(String username);
}
