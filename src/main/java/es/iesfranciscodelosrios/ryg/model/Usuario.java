package es.iesfranciscodelosrios.ryg.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nombre_comercio")
	private String nombre_comercio;
	@Column(name = "contrasena")
	private String contrasena;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "email")
	private String email;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "latitud")
	private float latitud;
	@Column(name = "longitud")
	private float longitud;
	@Column(name = "participaciones")
	private int participaciones;

	@JsonIgnoreProperties(value = {"usuario"}, allowSetters = true)
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Boleto> boletos;

	/**
	 * @param id
	 * @param nombre_comercio
	 * @param contrasena
	 * @param direccion
	 * @param email
	 * @param telefono
	 * @param latitud
	 * @param longitud
	 */
	public Usuario(Long id, String nombre_comercio, String contrasena, String direccion, String email, String telefono,
			float latitud, float longitud) {
		super();
		this.id = id;
		this.nombre_comercio = nombre_comercio;
		this.contrasena = contrasena;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	/**
	 * @param nombre_comercio
	 * @param contrasena
	 * @param direccion
	 * @param email
	 * @param telefono
	 * @param latitud
	 * @param longitud
	 */
	public Usuario(String nombre_comercio, String contrasena, String direccion, String email, String telefono,
			float latitud, float longitud, int participaciones) {
		super();
		this.nombre_comercio = nombre_comercio;
		this.contrasena = contrasena;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
		this.latitud = latitud;
		this.longitud = longitud;
		this.participaciones = participaciones;
	}

	/**
	 * 
	 */
	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_comercio() {
		return nombre_comercio;
	}

	public void setNombre_comercio(String nombre_comercio) {
		this.nombre_comercio = nombre_comercio;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public int getParticipaciones() {
		return participaciones;
	}

	public void setParticipaciones(int participaciones) {
		this.participaciones = participaciones;
	}

	public List<Boleto> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<Boleto> boletos) {
		this.boletos = boletos;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
