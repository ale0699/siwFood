package it.uniroma3.siwFood.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwFood.model.Ricetta;

public interface RicettaRepository extends CrudRepository<Ricetta, Long> {
	
	public List<Ricetta> findAllByIngredientiNomeContainsAllIgnoreCase(String nomeIngrediente);
	
	public List<Ricetta> findAllByNomeContainsAllIgnoreCase(String nomeRicetta);
	
	public List<Ricetta> findAllByCuocoIdCuoco(Long idCuoco);
}
