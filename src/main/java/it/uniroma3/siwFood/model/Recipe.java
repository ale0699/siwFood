package it.uniroma3.siwFood.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "idRecipe")

@Entity
public class Recipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idRecipe;
	@Column(nullable = false)
	private String name;
	private List<String> pictureRecipe;
	@Column(nullable = false, length = 100000)
	private String description;
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE) //cascade remove, quando elimino una ricetta elimino i suoi ingredienti
	//definisci fetch
	private List<Ingredient> ingredients;
	
	@ManyToOne
	private Cook cook;
	
	public Recipe() {
		
		this.pictureRecipe = new ArrayList<>();
	}
	
	public Long getIdRecipe() {
		return idRecipe;
	}
	public void setIdRecipe(Long idRecipe) {
		this.idRecipe = idRecipe;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPictureRecipe() {
		return pictureRecipe;
	}
	public void setPictureRecipe(List<String> pictureRecipe) {
		this.pictureRecipe = pictureRecipe;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Cook getCook() {
		return cook;
	}

	public void setCook(Cook cuoco) {
		this.cook = cuoco;
	}


	@Override
	public String toString() {
		return "Ricetta [idRecipe=" + idRecipe + ", name=" + name + ", pictureRecipe=" + pictureRecipe
				+ ", description=" + description + ", ingredients=" + ingredients + ", cook=" + cook + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cook, description, idRecipe, ingredients, name, pictureRecipe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Objects.equals(cook, other.cook) && Objects.equals(description, other.description)
				&& Objects.equals(idRecipe, other.idRecipe) && Objects.equals(ingredients, other.ingredients)
				&& Objects.equals(name, other.name) && Objects.equals(pictureRecipe, other.pictureRecipe);
	}

}
