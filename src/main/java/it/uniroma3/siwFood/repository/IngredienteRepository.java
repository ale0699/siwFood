package it.uniroma3.siwFood.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
	
	public List<Ingrediente> findAllByRicettaIdRicetta(Long idRicetta);
}
