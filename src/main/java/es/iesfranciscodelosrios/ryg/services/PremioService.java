package es.iesfranciscodelosrios.ryg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.Premio;
import es.iesfranciscodelosrios.ryg.repository.PremioRepository;

@Service
public class PremioService {
	@Autowired
	PremioRepository premiosrepository;

	/**
	 * Método que devuelve todos los premios, haciendo uso del repositorio del mismo
	 * 
	 * @return Lista con todos los premios existentes
	 */
	public List<Premio> getAllPremios() {
		List<Premio> premiosList = premiosrepository.findAll();

		if (premiosList.size() > 0) {
			return premiosList;
		} else {
			return new ArrayList<Premio>();
		}
	}

	/**
	 * Método que obtiene un premio de la base de datos buscando por su id, si existe,
	 * lo devuelve, si no, lanza una excepción para dicho resultado
	 * @param id
	 * @return premio si lo encuentra, excepción si no lo encuentra
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 */
	public Premio getPremiosById(Integer id) throws RecordNotFoundException, NullPointerException {
		
		if(id!=null) {
			try {
				Optional<Premio> premio = premiosrepository.findById(id);
				if (premio.isPresent()) {
					return premio.get();
				} else {
					throw new RecordNotFoundException("No premio record exist for given id", id);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		}else {
			throw new NullPointerException("El id es un objeto nulo");
		}
		
	}

	
	/**
	 * Método que crea un premio en la base de datos, si existe el objeto referenciado.
	 * Si el objeto ya estaba persistido, se ejecuta el método de actualizar
	 * @param entity
	 * @return premio creado en la base de datos
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Premio createPremio(Premio entity) throws NullPointerException, IllegalArgumentException{
		if (entity != null) {
			if (entity.getId() == -1) {
				entity.setId(-1);
				try {
					return entity = premiosrepository.save(entity);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return updatePremio(entity);
			}
		} else {
			throw new NullPointerException("El ticket es un objeto nulo");
		}
	
	}

	/**
	 * Método que actualiza la información de un premio en la base de datos
	 * @param entity
	 * @return premio actualizado
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws RecordNotFoundException
	 */
	public Premio updatePremio(Premio entity) 
			throws NullPointerException, IllegalArgumentException, RecordNotFoundException {

		if (entity!=null) {
			Optional<Premio> premio = premiosrepository.findById(entity.getId());

			if (premio.isPresent()) {
				Premio newEntity = premio.get();

				newEntity.setDescription(entity.getDescription());
				newEntity.setEntregado(entity.isEntregado());

				try {
					return premiosrepository.save(newEntity);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				throw new RecordNotFoundException("Premio not found", entity.getId());
			}
		} else {
			throw new NullPointerException("El premio es un objeto nulo");
		}
	}

	/**
	 * Método que borra un premio de la base de datos, si no lo encuentra lanza
	 * una excepción
	 * @param id
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void deletePremioById(Integer id) 
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		
		if (id!=null) {
			Optional<Premio> premio = premiosrepository.findById(id);

			if (premio.isPresent()) {
				try {
					premiosrepository.deleteById(id);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}

			} else {
				throw new RecordNotFoundException("No premio record exist for given id", id);
			}
		} else {
			throw new NullPointerException("El id es un objeto nulo");
		}
		
	}

	public List<Premio> getPremiosByDescription(String description) {
		List<Premio> premiosList = premiosrepository.getByDescripcion(description);

		if (premiosList.size() > 0) {
			return premiosList;
		} else {
			return new ArrayList<Premio>();
		}

	}

}
