package es.iesfranciscodelosrios.ryg.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="infoTicket")
public class InfoTicket implements Serializable{

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
	private Timestamp fechaTicket;
	@Column(name = "nombre_comercio")
	private String nombreComercio;
	
	
	
    /// private int idBoleto   
	
	public InfoTicket() {}
	
	public InfoTicket(Long id, String nombreCliente, int telefono, int numeroTicket, Timestamp fechaTicket,
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

	public Timestamp getFechaTicket() {
		return fechaTicket;
	}

	public void setFechaTicket(Timestamp fechaTicket) {
		this.fechaTicket = fechaTicket;
	}

	public String getNombreComercio() {
		return nombreComercio;
	}

	public void setNombreComercio(String nombreComercio) {
		this.nombreComercio = nombreComercio;
	}

	@Override
	public String toString() {
		return "InfoTicket [id=" + id + ", nombreCliente=" + nombreCliente + ", telefono=" + telefono
				+ ", numeroTicket=" + numeroTicket + ", fechaTicket=" + fechaTicket + ", nombreComercio="
				+ nombreComercio + "]";
	}
	
	
	 
	
	
	
	
	
}
