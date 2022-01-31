package es.iesfranciscodelosrios.ryg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.Usuario;
import es.iesfranciscodelosrios.ryg.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository repository;

	/**
	 * Método que devuelve todos los usuarios, haciendo uso del repositorio del mismo
	 * 
	 * @return Lista con todos los usuarios existentes
	 */
	public List<Usuario> getAllUsuario() {
		List<Usuario> getAllUsuario = repository.findAll();
		return getAllUsuario;
	}

	/**
	 * Método que obtiene un usuario de la base de datos buscando por su id, si
	 * existe, lo devuelve, si no, lanza una excepción para dicho resultado
	 * 
	 * @param id
	 * @return usuario si lo encuentra, excepcion si no lo encuentra
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 */
	public Usuario getUsuarioById(Long id) throws RecordNotFoundException, NullPointerException {
		if (id != null) {
			try {
				Optional<Usuario> getUsuarioById = repository.findById(id);
				if (getUsuarioById.isPresent()) {
					return getUsuarioById.get();
				} else {
					throw new RecordNotFoundException("La nota no existe", id);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("El id es un objeto nulo");
		}
	}

	/**
	 * Método que crea un usuario en la base de datos, si existe el objeto
	 * referenciado. Si el objeto ya estaba persistido, se ejecuta el método
	 * de actualizar
	 * @param usuario
	 * @return Usuario creado en la base de datos 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Usuario createUsuario(Usuario usuario) throws NullPointerException, IllegalArgumentException {
		if (usuario != null) {
			if (usuario.getId() != -1) {
				usuario.setId(null);
				try {
					return usuario = repository.save(usuario);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return updateUsuario(usuario);
			}
		} else {
			throw new NullPointerException("El usuario es un objeto nulo");
		}
	}

	/**
	 * Método que actualiza la información de un usuario de la base de datos
	 * @param usuario
	 * @return usuario actualizado
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws RecordNotFoundException
	 */
	public Usuario updateUsuario(Usuario usuario)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (usuario != null) {
			Optional<Usuario> getUsuario = Optional.ofNullable(getUsuarioById(usuario.getId()));
			if (!getUsuario.isEmpty()) {
				Usuario updateUsuario = getUsuario.get();
				updateUsuario.setId(usuario.getId());
				updateUsuario.setNombre_comercio(usuario.getNombre_comercio());
				updateUsuario.setContrasena(usuario.getContrasena());
				updateUsuario.setDireccion(usuario.getDireccion());
				updateUsuario.setEmail(usuario.getEmail());
				updateUsuario.setTelefono(usuario.getTelefono());
				updateUsuario.setLatitud(usuario.getLatitud());
				updateUsuario.setLongitud(usuario.getLongitud());
				try {
					return repository.save(updateUsuario);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				throw new RecordNotFoundException("La nota no existe", usuario.getId());
			}
		} else {
			throw new NullPointerException("El usuario es un objeto nulo");
		}
	}

	/**
	 * Método que borra un usuario de la base de datos, si no lo encuentra
	 * lanza una excepción
	 * @param id
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void deleteUsuarioById(Long id)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			Optional<Usuario> deleteUsuarioById = Optional.ofNullable(getUsuarioById(id));
			if (!deleteUsuarioById.isEmpty()) {
				try {
					repository.deleteById(id);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				throw new RecordNotFoundException("La nota no existe", id);
			}
		} else {
			throw new NullPointerException("El id es un objeto nulo");
		}
	}
}
