package es.iesfranciscodelosrios.ryg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Premio")
public class Premios {
	// id descriocion entregado
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// @NotBlank
	@Column(name = "Descripcion")
	private String description;
	@NotBlank
	@Column(name = "Entregado")
	private boolean entregado;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

}
