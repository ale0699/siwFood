package it.uniroma3.siwFood.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Credentials {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCredentials;
	@Column(unique = true, nullable = false)
	private String username;
	private String password;
	private String role;
	
	public Credentials() {
		
	}

	public Long getIdCredentials() {
		return idCredentials;
	}

	public void setIdCredentials(Long idCredentials) {
		this.idCredentials = idCredentials;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Credenziali [idCredentials=" + idCredentials + ", username=" + username + ", password=" + password
				+ ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCredentials, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		return Objects.equals(idCredentials, other.idCredentials) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}
}
