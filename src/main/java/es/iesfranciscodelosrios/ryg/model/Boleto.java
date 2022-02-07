package es.iesfranciscodelosrios.ryg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "boleto")
public class Boleto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descripcion", length = 256)
	private String descripcion;
	@Column(name = "entregado")
	private boolean entregado;
	@Column(name = "canjeado")
	private boolean canjeado;

	@JsonIgnoreProperties(value = {"boleto"}, allowSetters = true)
	@OneToOne
	@JoinColumn(nullable = true, name = "id_premio")
	private Premio premio;

	@JsonIgnoreProperties(value = {"boleto"}, allowSetters = true)
	@OneToOne(mappedBy = "boleto")
	private InfoTicket ticket;

	@JsonIgnoreProperties(value = {"boletos"}, allowSetters = true)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Boleto(Long id, String descripcion, boolean entregado, boolean canjeado, Premio premio, InfoTicket ticket,
			Usuario usuario) {
		this.id = id;
		this.descripcion = descripcion;
		this.entregado = entregado;
		this.canjeado = canjeado;
		this.premio = premio;
		this.ticket = ticket;
		this.usuario = usuario;
	}

	public Boleto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripicion) {
		this.descripcion = descripicion;
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

	public Premio getPremio() {
		return premio;
	}

	public void setPremio(Premio premio) {
		this.premio = premio;
	}

	public InfoTicket getTicket() {
		return ticket;
	}

	public void setTicket(InfoTicket ticket) {
		this.ticket = ticket;
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
