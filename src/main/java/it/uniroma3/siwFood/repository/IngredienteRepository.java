package it.uniroma3.siwFood.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
	
}
