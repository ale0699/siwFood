package it.uniroma3.siwFood.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cuoco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCuoco;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	@Column(nullable = false)
	private LocalDate dataNascita;
	private String fotografia;
	
	@OneToMany(mappedBy = "cuoco")
	private List<Ricetta> ricette;
	
	public Cuoco() {
		
		this.ricette = new ArrayList<>();
	}

	public Long getIdCuoco() {
		return idCuoco;
	}

	public void setIdCuoco(Long idCuoco) {
		this.idCuoco = idCuoco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getFotografia() {
		return fotografia;
	}

	public void setFotografia(String fotografia) {
		this.fotografia = fotografia;
	}

	public List<Ricetta> getRicette() {
		return ricette;
	}

	public void setRicette(List<Ricetta> ricette) {
		this.ricette = ricette;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataNascita, fotografia, idCuoco, nome, ricette);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuoco other = (Cuoco) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(fotografia, other.fotografia) && Objects.equals(idCuoco, other.idCuoco)
				&& Objects.equals(nome, other.nome) && Objects.equals(ricette, other.ricette);
	}

	@Override
	public String toString() {
		return "Cuoco [idCuoco=" + idCuoco + ", nome=" + nome + ", cognome=" + cognome + ", dataNascita=" + dataNascita
				+ ", fotografia=" + fotografia + ", ricette=" + ricette + "]";
	}
}
