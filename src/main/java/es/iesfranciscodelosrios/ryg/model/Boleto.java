package es.iesfranciscodelosrios.ryg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Boleto")
public class Boleto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descripcion", length = 256)
	private String descripicion;
	@Column(name = "entregado")
	private boolean entregado;
	@Column(name = "canjeado")
	private boolean canjeado;
	// private Premio premio;
	@Column(name = "id_usuario")
	private Usuario usuario;

	public Boleto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripicion() {
		return descripicion;
	}

	public void setDescripicion(String descripicion) {
		this.descripicion = descripicion;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public boolean isCanjeado() {
		return canjeado;
	}

	public void setCanjeado(boolean canjeado) {
		this.canjeado = canjeado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Boleto [id=" + id + ", descripicion=" + descripicion + ", entregado=" + entregado + ", canjeado="
				+ canjeado + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Boleto other = (Boleto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
