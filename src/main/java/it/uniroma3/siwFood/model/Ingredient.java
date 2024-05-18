package it.uniroma3.siwFood.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idIngredient;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String quantity;
	
	@ManyToOne
	private Recipe recipe;
	
	public Ingredient() {
		
	}
	public Long getIdIngredient() {
		return idIngredient;
	}
	public void setIdIngredient(Long idIngredient) {
		this.idIngredient = idIngredient;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	@Override
	public String toString() {
		return "Ingrediente [idIngredient=" + idIngredient + ", name=" + name + ", quantity=" + quantity + ", recipe="
				+ recipe + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idIngredient, name, quantity, recipe);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		return Objects.equals(idIngredient, other.idIngredient) && Objects.equals(name, other.name)
				&& Objects.equals(quantity, other.quantity) && Objects.equals(recipe, other.recipe);
	}	
}
