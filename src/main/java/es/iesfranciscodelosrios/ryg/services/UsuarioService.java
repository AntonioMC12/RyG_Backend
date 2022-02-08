package es.iesfranciscodelosrios.ryg.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.Usuario;
import es.iesfranciscodelosrios.ryg.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private static final Logger logger = LogManager.getLogger("UsuarioService");

	@Autowired
	UsuarioRepository repository;

	/**
	 * Método que devuelve todos los usuarios, haciendo uso del repositorio del
	 * mismo
	 * 
	 * @return Lista con todos los usuarios existentes
	 * @throws Exception
	 */
	public List<Usuario> getAllUsuario() throws Exception {
		try {
			List<Usuario> getAllUsuario = repository.findAll();
			return getAllUsuario;
		} catch (Exception e) {
			logger.error("The boleto doesn't exists in the database." + e);
			throw new Exception(e);
		}
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
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("El usuario no existe", id);
				}
			} catch (IllegalArgumentException e) {
				logger.error("The boleto doesn't exists in the database." + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El id es un objeto nulo");
		}
	}

	/**
	 * Método que crea un usuario en la base de datos, si existe el objeto
	 * referenciado. Si el objeto ya estaba persistido, se ejecuta el método de
	 * actualizar
	 * 
	 * @param usuario
	 * @return Usuario creado en la base de datos
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Usuario createUsuario(Usuario usuario) throws NullPointerException, IllegalArgumentException {
		if (usuario != null) {
			if (usuario.getId() == -1) {
				usuario.setId(null);
				try {
					return repository.save(usuario);
				} catch (IllegalArgumentException e) {
					logger.error("The boleto doesn't exists in the database." + e);
					throw new IllegalArgumentException(e);
				}
			} else {
				return updateUsuario(usuario);
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El usuario es un objeto nulo");
		}
	}

	/**
	 * Método que actualiza la información de un usuario de la base de datos
	 * 
	 * @param usuario
	 * @return usuario actualizado
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws RecordNotFoundException
	 */
	public Usuario updateUsuario(Usuario usuario)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (usuario != null) {
			try {
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
					return repository.save(updateUsuario);
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("El usuario no existe", usuario.getId());
				}
			} catch (IllegalArgumentException e) {
				logger.error("The boleto doesn't exists in the database." + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El usuario es un objeto nulo");
		}
	}

	/**
	 * Método que borra un usuario de la base de datos, si no lo encuentra lanza una
	 * excepción
	 * 
	 * @param id
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void deleteUsuarioById(Long id)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			try {
				Optional<Usuario> deleteUsuarioById = Optional.ofNullable(getUsuarioById(id));
				if (!deleteUsuarioById.isEmpty()) {
					repository.deleteById(id);
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("El usuario no existe", id);
				}
			} catch (IllegalArgumentException e) {
				logger.error("The boleto doesn't exists in the database." + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El id es un objeto nulo");
		}
	}

	/**
	 * Método que obtiene un usuario de la base de datos buscando por su latitud y
	 * longitud, si existe, lo devuelve, si no, lanza una excepción para dicho
	 * resultado
	 * 
	 * @param latitud
	 * @param longitud
	 * @return usuario si lo encuentra, excepcion si no lo encuentra
	 * @throws Exception
	 */
	public Usuario getUsuarioByCoordinates(float latitud, float longitud) throws Exception {
		try {
			Optional<Usuario> getUsuarioByCoordinates = repository.getUsuarioByCoordinates(latitud, longitud);
			if (getUsuarioByCoordinates.isPresent()) {
				return getUsuarioByCoordinates.get();
			} else {
				logger.error("The boleto doesn't exists in the database.");
				throw new RecordNotFoundException("No existe ningún usuario con esas coordenadas",
						latitud + "-" + longitud);
			}
		} catch (IllegalArgumentException e) {
			logger.error("The boleto doesn't exists in the database." + e);
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			logger.error("The boleto doesn't exists in the database." + e);
			throw new Exception();
		}
	}
}
