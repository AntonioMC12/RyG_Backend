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

@Entity
@Table(name = "info_ticket")
public class InfoTicket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nombre_cliente")
	private String nombreCliente;
	@Column(name = "telefono")
	private int telefono;
	@Column(name = "numero_ticket")
	private int numeroTicket;
	@Column(name = "fecha_ticket")
	private LocalDate fechaTicket;
	@Column(name = "nombre_comercio")
	private String nombreComercio;

	@JsonIgnoreProperties(value = {"ticket"}, allowSetters = true)
	@OneToOne
	@JoinColumn(name = "id_boleto")
	private Boleto boleto;

	public InfoTicket() {
	}

	public InfoTicket(Long id, String nombreCliente, int telefono, int numeroTicket, LocalDate fechaTicket,
			String nombreComercio) {
		super();
		this.id = id;
		this.nombreCliente = nombreCliente;
		this.telefono = telefono;
		this.numeroTicket = numeroTicket;
		this.fechaTicket = fechaTicket;
		this.nombreComercio = nombreComercio;
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
