package it.uniroma3.siwFood.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ricetta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idRicetta;
	@Column(nullable = false)
	private String nome;
	private List<String> immaginiRicetta;
	@Column(nullable = false)
	private String descrizione;
	
	@OneToMany(mappedBy = "ricetta", cascade = CascadeType.REMOVE) //cascade remove, quando elimino una ricetta elimino i suoi ingredienti
	//definisci fetch
	private List<Ingrediente> ingredienti;
	
	@ManyToOne
	private Cuoco cuoco;
	
	public Ricetta() {
		
		this.immaginiRicetta = new ArrayList<>();
	}
	
	public Long getIdRicetta() {
		return idRicetta;
	}
	public void setIdRicetta(Long idRicetta) {
		this.idRicetta = idRicetta;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<String> getImmaginiRicetta() {
		return immaginiRicetta;
	}
	public void setImmaginiRicetta(List<String> immaginiRicetta) {
		this.immaginiRicetta = immaginiRicetta;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public Cuoco getCuoco() {
		return cuoco;
	}

	public void setCuoco(Cuoco cuoco) {
		this.cuoco = cuoco;
	}

	@Override
	public String toString() {
		return "Ricetta [idRicetta=" + idRicetta + ", nome=" + nome + ", immaginiRicetta=" + immaginiRicetta
				+ ", descrizione=" + descrizione + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(descrizione, idRicetta, immaginiRicetta, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ricetta other = (Ricetta) obj;
		return Objects.equals(descrizione, other.descrizione) && Objects.equals(idRicetta, other.idRicetta)
				&& Objects.equals(immaginiRicetta, other.immaginiRicetta) && Objects.equals(nome, other.nome);
	}
}
