package es.iesfranciscodelosrios.ryg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
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
			float latitud, float longitud) {
		super();
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
	 * @param email
	 */
	public Usuario(String nombre_comercio, String contrasena, String email) {
		super();
		this.nombre_comercio = nombre_comercio;
		this.contrasena = contrasena;
		this.email = email;
	}

	/**
	 * @param contrasena
	 * @param email
	 */
	public Usuario(String contrasena, String email) {
		super();
		this.contrasena = contrasena;
		this.email = email;
	}

	/**
	 * 
	 */
	public Usuario() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contrasena == null) ? 0 : contrasena.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(latitud);
		result = prime * result + Float.floatToIntBits(longitud);
		result = prime * result + ((nombre_comercio == null) ? 0 : nombre_comercio.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
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
		Usuario other = (Usuario) obj;
		if (contrasena == null) {
			if (other.contrasena != null)
				return false;
		} else if (!contrasena.equals(other.contrasena))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Float.floatToIntBits(latitud) != Float.floatToIntBits(other.latitud))
			return false;
		if (Float.floatToIntBits(longitud) != Float.floatToIntBits(other.longitud))
			return false;
		if (nombre_comercio == null) {
			if (other.nombre_comercio != null)
				return false;
		} else if (!nombre_comercio.equals(other.nombre_comercio))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
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

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre_comercio=" + nombre_comercio + ", contrasena=" + contrasena
				+ ", direccion=" + direccion + ", email=" + email + ", telefono=" + telefono + ", latitud=" + latitud
				+ ", longitud=" + longitud + "]";
	}

}
