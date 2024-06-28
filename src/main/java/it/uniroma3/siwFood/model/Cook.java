package it.uniroma3.siwFood.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCook;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String surname;
	@Column(nullable = false)
	private LocalDate dateBirth;
	private String picture;
	
	@OneToMany(mappedBy = "cook", cascade = CascadeType.REMOVE)
	private List<Recipe> recipes;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Credentials credentials;
	
	public Cook() {
	    this.recipes = new ArrayList<>();
	}

	public Long getIdCook() {
		return idCook;
	}

	public void setIdCook(Long idCook) {
		this.idCook = idCook;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(LocalDate dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return "Cuoco [idCook=" + idCook + ", name=" + name + ", surname=" + surname + ", dateBirth=" + dateBirth
				+ ", picture=" + picture + ", recipes=" + recipes + ", credentials=" + credentials + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(credentials, dateBirth, idCook, name, picture, recipes, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cook other = (Cook) obj;
		return Objects.equals(credentials, other.credentials) && Objects.equals(dateBirth, other.dateBirth)
				&& Objects.equals(idCook, other.idCook) && Objects.equals(name, other.name)
				&& Objects.equals(picture, other.picture) && Objects.equals(recipes, other.recipes)
				&& Objects.equals(surname, other.surname);
	}

}
