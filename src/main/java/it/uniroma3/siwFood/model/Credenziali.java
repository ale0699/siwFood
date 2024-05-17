package it.uniroma3.siwFood.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Credenziali {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCredenziali;
	private String username;
	private String password;
	private String ruolo;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Cuoco cuoco;
	
	public Credenziali() {
		
	}

	public Long getIdCredenziali() {
		return idCredenziali;
	}

	public void setIdCredenziali(Long idCredenziali) {
		this.idCredenziali = idCredenziali;
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

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Cuoco getCuoco() {
		return cuoco;
	}

	public void setCuoco(Cuoco cuoco) {
		this.cuoco = cuoco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cuoco, idCredenziali, password, ruolo, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credenziali other = (Credenziali) obj;
		return Objects.equals(cuoco, other.cuoco) && Objects.equals(idCredenziali, other.idCredenziali)
				&& Objects.equals(password, other.password) && Objects.equals(ruolo, other.ruolo)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Credenziali [idCredenziali=" + idCredenziali + ", username=" + username + ", password=" + password
				+ ", ruolo=" + ruolo + ", cuoco=" + cuoco + "]";
	}
}
