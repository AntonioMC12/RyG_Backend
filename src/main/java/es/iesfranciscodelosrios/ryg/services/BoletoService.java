package es.iesfranciscodelosrios.ryg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.Boleto;
import es.iesfranciscodelosrios.ryg.repository.BoletoRepository;

@Service
public class BoletoService {

	@Autowired
	BoletoRepository repository;

	/**
	 * Método que devuelve todas los boletos, haciendo uso del repositorio del mismo
	 * 
	 * @return Lista con todos los boletos existentes.
	 */
	public List<Boleto> getAllBoleto() {
		List<Boleto> getAllBoleto = repository.findAll();
		return getAllBoleto;
	}

	/**
	 * Método que obtiene un boleto de la base de datos buscando por su id, si
	 * existe, este lo devuelve si no existe lanza una excepción para tratar dicho
	 * resultado.
	 * 
	 * @param id del boleto a buscar
	 * @return boleto si lo encuentra, excepcion si no lo encuentra.
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 */
	public Boleto getBoletoById(Long id) throws RecordNotFoundException, NullPointerException {
		if (id != null) {
			try {
				Optional<Boleto> getBoletoById = repository.findById(id);
				if (getBoletoById.isPresent()) {
					return getBoletoById.get();
				} else {
					throw new RecordNotFoundException("El boleto no existe", id);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			throw new NullPointerException("El id es un objeto nulo");
		}

	}

	/**
	 * Método que crea un boleto en la base de datos, si existe el objeto
	 * referenciado. Si el objeto ya está persistido (Su id es distinto de -1 se
	 * ejecuta el método de "Actualizar".
	 * 
	 * @param boleto a guardar en la base de datos.
	 * @return boleto creado en la base de datos con el id actualizado.
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Boleto createBoleto(Boleto boleto) throws NullPointerException, IllegalArgumentException {
		if (boleto != null) {
			if (boleto.getId() == -1) {
				boleto.setId(null);
				try {
					return boleto = repository.save(boleto);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				return updateBoleto(boleto);
			}
		} else {
			throw new NullPointerException("He boleto es un objeto nulo");
		}
	}

	/**
	 * Método que actualiza un boleto de la base de datos, si este boleto no es nulo
	 * y se encuentra en la base de datos.
	 * 
	 * @param boleto a actualizar en la base de datos.
	 * @return boleto actualizado.
	 * @throws NullPointerException
	 * @throws RecordNotFoundException
	 * @throws IllegalArgumentException
	 */
	public Boleto updateBoleto(Boleto boleto)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (boleto != null) {
			Optional<Boleto> getBoleto = Optional.ofNullable(getBoletoById(boleto.getId()));
			if (!getBoleto.isEmpty()) {
				Boleto updateBoleto = getBoleto.get();
				updateBoleto.setId(boleto.getId());
				updateBoleto.setDescripicion(boleto.getDescripicion());
				updateBoleto.setUsuario(boleto.getUsuario());
				updateBoleto.setCanjeado(boleto.isCanjeado());
				updateBoleto.setEntregado(boleto.isEntregado());
				try {
					return repository.save(updateBoleto);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				throw new RecordNotFoundException("El boleto no existe", boleto.getId());
			}
		} else {
			throw new NullPointerException("El boleto es un objeto nulo");
		}
	}

	/**
	 * Método que borra un boleto de la base de datos si lo encuentra, si no lo
	 * encuentra lanza una excepción.
	 * 
	 * @param id del boleto que queremos borrar.
	 * @throws NullPointerException
	 * @throws RecordNotFoundException
	 * @throws IllegalArgumentException
	 */
	public void deleteBoletoById(Long id)
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException {
		if (id != null) {
			Optional<Boleto> deleteBoletoById = Optional.ofNullable(getBoletoById(id));
			if (!deleteBoletoById.isEmpty()) {
				try {
					repository.deleteById(id);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			} else {
				throw new RecordNotFoundException("El boleto no existe", id);
			}
		} else {
			throw new NullPointerException("El id es un objeto nulo");
		}
	}

}
