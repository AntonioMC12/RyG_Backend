package es.iesfranciscodelosrios.ryg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.Premio;
import es.iesfranciscodelosrios.ryg.repository.PremioRepository;

@Service
public class PremioService {

	private static final Logger logger = LogManager.getLogger("PremioService");

	@Autowired
	PremioRepository repository;

	/**
	 * Método que devuelve todos los premios, haciendo uso del repositorio del mismo
	 * 
	 * @return Lista con todos los premios existentes
	 * @throws Exception
	 */
	public List<Premio> getAllPremios() throws Exception {
		try {
			List<Premio> premiosList = repository.findAll();
			if (premiosList.size() > 0) {
				return premiosList;
			} else {
				return new ArrayList<Premio>();
			}
		} catch (Exception e) {
			logger.error("The boleto doesn't exists in the database." + e);
			throw new Exception("El boleto no existe", e);
		}
	}

	/**
	 * Método que obtiene un premio de la base de datos buscando por su id, si
	 * existe, lo devuelve, si no, lanza una excepción para dicho resultado
	 * 
	 * @param id
	 * @return premio si lo encuentra, excepción si no lo encuentra
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 */
	public Premio getPremiosById(Integer id) throws RecordNotFoundException, NullPointerException {
		if (id != null) {
			try {
				Optional<Premio> premio = repository.findById(id);
				if (premio.isPresent()) {
					return premio.get();
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("No premio record exist for given id", id);
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
	 * Método que crea un premio en la base de datos, si existe el objeto
	 * referenciado. Si el objeto ya estaba persistido, se ejecuta el método de
	 * actualizar
	 * 
	 * @param entity
	 * @return premio creado en la base de datos
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Premio createPremio(Premio entity)
			throws NullPointerException, IllegalArgumentException, RecordNotFoundException, Exception {
		if (entity != null) {
			if (entity.getId() == -1) {
				entity.setId(-1);
				try {
					return entity = repository.save(entity);
				} catch (IllegalArgumentException e) {
					logger.error("The boleto doesn't exists in the database." + e);
					throw new IllegalArgumentException(e);
				}
			} else {
				try {
					return updatePremio(entity);

				} catch (IllegalArgumentException e) {
					logger.error("The boleto doesn't exists in the database." + e);
					throw new IllegalArgumentException(e);
				} catch (NullPointerException e) {
					logger.error("The boleto doesn't exists in the database." + e);
					throw new NullPointerException();
				} catch (RecordNotFoundException e) {
					logger.error("The boleto doesn't exists in the database." + e);
					throw new RecordNotFoundException("No premio record exist for given id", e);
				} catch (Exception e) {
					logger.error("The boleto doesn't exists in the database." + e);
					throw new Exception(e);
				}
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El ticket es un objeto nulo");
		}

	}

	/**
	 * Método que actualiza la información de un premio en la base de datos
	 * 
	 * @param entity
	 * @return premio actualizado
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws RecordNotFoundException
	 */
	public Premio updatePremio(Premio entity)
			throws NullPointerException, IllegalArgumentException, RecordNotFoundException {

		if (entity != null) {
			try {
				Optional<Premio> premio = repository.findById(entity.getId());
				if (premio.isPresent()) {
					Premio newEntity = premio.get();
					newEntity.setDescription(entity.getDescription());
					newEntity.setEntregado(entity.isEntregado());
					return repository.save(newEntity);
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("Premio not found", entity.getId());
				}
			} catch (IllegalArgumentException e) {
				logger.error("The boleto doesn't exists in the database." + e);
				throw new IllegalArgumentException(e);
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El premio es un objeto nulo");
		}
	}

	/**
	 * Método que borra un premio de la base de datos, si no lo encuentra lanza una
	 * excepción
	 * 
	 * @param id
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void deletePremioById(Integer id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {

		if (id != null) {
			try {
				Optional<Premio> premio = repository.findById(id);
				if (premio.isPresent()) {
					repository.deleteById(id);
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("No premio record exist for given id", id);
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

	public List<Premio> getPremiosByDescription(String description) throws Exception {
		try {
			List<Premio> premiosList = repository.getByDescripcion(description);
			if (premiosList.size() > 0) {
				return premiosList;
			} else {
				return new ArrayList<Premio>();
			}
		} catch (Exception e) {
			logger.error("The boleto doesn't exists in the database." + e);
			throw new Exception(e);
		}

	}

	/**
	 * Método que devuelve todas los premios que han sido entregados, haciendo uso
	 * del repositorio del mismo
	 * 
	 * @return Lista con todos los premios entregados
	 * @throws Exception
	 */
	public List<Premio> getPremiosEntregados() throws Exception {
		try {
			List<Premio> getPremiosEntregados = repository.getPremiosEntregados();
			return getPremiosEntregados;
		} catch (Exception e) {
			logger.error("The boleto doesn't exists in the database." + e);
			throw new Exception(e);
		}
	}

	/**
	 * Método que devuelve todas los premios no que han sido entregados, haciendo
	 * uso del repositorio del mismo
	 * 
	 * @return Lista con todos los premios no entregados
	 * @throws Exception
	 */
	public List<Premio> getPremiosNoEntregados() throws Exception {
		try {
			List<Premio> getPremiosNoEntregados = repository.getPremiosNoEntregados();
			return getPremiosNoEntregados;
		} catch (Exception e) {
			logger.error("The boleto doesn't exists in the database." + e);
			throw new Exception(e);
		}
	}

}
