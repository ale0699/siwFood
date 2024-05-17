package it.uniroma3.siwFood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Cuoco;

public interface CuocoRepository extends CrudRepository<Cuoco, Long> {
	
	public Cuoco findByCredenzialiIdCredenziali(Long idCredenziali);
}
