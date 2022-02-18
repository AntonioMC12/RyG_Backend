package es.iesfranciscodelosrios.ryg.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Modelo de Ticket")
@Entity
@Table(name = "info_ticket")
public class InfoTicket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(
			value="Identificador",
			example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@ApiModelProperty(
			value="Nombre del cliente al que pertenece")
	@Column(name = "nombre_cliente")
	private String nombreCliente;
	@ApiModelProperty(
			value="Teléfono del cliente",
			example = "669317799")
	@Column(name = "telefono")
	private int telefono;
	@ApiModelProperty(
			value="Número del ticket",
			example = "092")
	@Column(name = "numero_ticket")
	private int numeroTicket;
	@ApiModelProperty(
			value="Fecha de salida del ticket",
			example = "13-04-22")
	@Column(name = "fecha_ticket")
	private LocalDate fechaTicket;
	@ApiModelProperty(
			value="Nombre del comercio que da el ticket")
	@Column(name = "nombre_comercio")
	private String nombreComercio;
	@ApiModelProperty(
			value="Imagen")
	@Column(name = "foto")
	private String foto;
	
	@JsonIgnoreProperties(value = { "ticket" }, allowSetters = true)
	@OneToOne
	@JoinColumn(name = "id_boleto")
	private Boleto boleto;

	public InfoTicket() {
	}

	public InfoTicket(Long id, String nombreCliente, int telefono, int numeroTicket, LocalDate fechaTicket,
			String nombreComercio, String foto) {
		super();
		this.id = id;
		this.nombreCliente = nombreCliente;
		this.telefono = telefono;
		this.numeroTicket = numeroTicket;
		this.fechaTicket = fechaTicket;
		this.nombreComercio = nombreComercio;
		this.foto = foto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getNumeroTicket() {
		return numeroTicket;
	}

	public void setNumeroTicket(int numeroTicket) {
		this.numeroTicket = numeroTicket;
	}

	public LocalDate getFechaTicket() {
		return fechaTicket;
	}

	public void setFechaTicket(LocalDate fechaTicket) {
		this.fechaTicket = fechaTicket;
	}

	public String getNombreComercio() {
		return nombreComercio;
	}

	public void setNombreComercio(String nombreComercio) {
		this.nombreComercio = nombreComercio;
	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoTicket other = (InfoTicket) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
