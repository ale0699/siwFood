package it.uniroma3.siwFood.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idIngrediente;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String quantita;
	
	public Ingrediente() {
		
	}
	public Long getIdIngrediente() {
		return idIngrediente;
	}
	public void setIdIngrediente(Long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	
	@Override
	public String toString() {
		return "Ingrediente [idIngrediente=" + idIngrediente + ", nome=" + nome + ", quantita=" + quantita + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idIngrediente, nome, quantita);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return Objects.equals(idIngrediente, other.idIngrediente) && Objects.equals(nome, other.nome)
				&& Objects.equals(quantita, other.quantita);
	}
	
}
