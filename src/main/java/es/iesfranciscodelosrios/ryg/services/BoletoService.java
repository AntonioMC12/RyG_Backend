package es.iesfranciscodelosrios.ryg.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.Boleto;
import es.iesfranciscodelosrios.ryg.repository.BoletoRepository;

@Service
public class BoletoService {

	public static final Logger logger = LoggerFactory.getLogger(BoletoService.class);

	@Autowired
	BoletoRepository repository;

	/**
	 * Método que devuelve todas los boletos, haciendo uso del repositorio del mismo
	 * 
	 * @return Lista con todos los boletos existentes.
	 * @throws Exception
	 */
	public List<Boleto> getAllBoleto() throws Exception {
		try {
			List<Boleto> getAllBoleto = repository.findAll();
			return getAllBoleto;
		} catch (Exception e) {
			logger.error("There is no boletos in the database " + e);
			throw new Exception("No hay boletos en la base de datos", e);
		}
	}

	/**
	 * Método que obtiene un boleto de la base de datos buscando por su id, si
	 * existe, este lo devuelve si no existe lanza una excepción para tratar dicho
	 * resultado.
	 * 
	 * @param id del boleto a buscar
	 * @return boleto si lo encuentra, excepcion si no lo encuentra.
	 * @throws Exception, IllegalArgumentException
	 */
	public Boleto getBoletoById(Long id)
			throws Exception, IllegalArgumentException, NullPointerException, RecordNotFoundException {
		if (id != null) {
			try {
				Optional<Boleto> getBoletoById = repository.findById(id);
				if (getBoletoById.isPresent()) {
					return getBoletoById.get();
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("El boleto no existe", id);
				}
			} catch (IllegalArgumentException e) {
				logger.error("IllegalArgumentException in the method getBoletoById: " + e);
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				logger.error("Exception in the method getBoletoById: " + e);
				throw new Exception(e);
			}
		} else {
			logger.error("NullPointerException in the method getBoletoById id equals to null.");
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
	 * @throws Exception, RecordNotFoundException, NullPointerException,
	 *                    IllegalArgumentException
	 */
	public Boleto createBoleto(Boleto boleto)
			throws Exception, RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if (boleto != null) {
			if (boleto.getId() == -1) {
				boleto.setId(null);
				try {
					if (checkPremios(boleto)) {
						return boleto = repository.save(boleto);
					} else {
						logger.error("You can't insert 2 or more boletos with the same premio");
						throw new Exception("No se puede insertar 2 o más boletos con el mismo premio");
					}
				} catch (IllegalArgumentException e) {
					logger.error("IllegalArgumentException in the method createBoleto" + e);
					throw new IllegalArgumentException(e);
				}
			} else {
				try {
					return updateBoleto(boleto);
				} catch (RecordNotFoundException e) {
					logger.error("RecordNotFoundException in the method createBoleto" + e);
					throw new RecordNotFoundException("El boleto no existe", e);
				} catch (NullPointerException e) {
					logger.error("NullPointerException in the method createBoleto" + e);
					throw new NullPointerException("El boleto no existe");
				} catch (IllegalArgumentException e) {
					logger.error("IllegalArgumentException in the method createBoleto" + e);
					throw new IllegalArgumentException("El boleto no existe");
				} catch (Exception e) {
					logger.error("Exception in the method createBoleto" + e);
					throw new Exception("El boleto no existe");
				}
			}
		} else {
			logger.error("NullPointerException in the method createBoleto boleto is null");
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
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException, Exception {
		if (boleto != null) {
			Optional<Boleto> getBoleto;
			try {
				getBoleto = Optional.ofNullable(getBoletoById(boleto.getId()));
				if (!getBoleto.isEmpty()) {
					Boleto updateBoleto = getBoleto.get();
					updateBoleto.setId(boleto.getId());
					updateBoleto.setDescripcion(boleto.getDescripcion());
					updateBoleto.setUsuario(boleto.getUsuario());
					updateBoleto.setCanjeado(boleto.isCanjeado());
					updateBoleto.setEntregado(boleto.isEntregado());
					if (checkPremios(updateBoleto)) {
						return repository.save(updateBoleto);
					} else {
						logger.error("You can't insert 2 or more boletos with the same premio");
						throw new Exception("No se puede insertar 2 o más boletos con el mismo premio");
					}
				} else {
					throw new RecordNotFoundException("El boleto no existe", boleto.getId());
				}
			} catch (IllegalArgumentException e1) {
				logger.error("Exception in the method updateBoleto" + e1);
				throw new IllegalArgumentException("El boleto no existe");
			} catch (NullPointerException e1) {
				logger.error("Exception in the method updateBoleto" + e1);
				throw new NullPointerException("El boleto no existe");
			} catch (RecordNotFoundException e1) {
				logger.error("Exception in the method updateBoleto" + e1);
				throw new RecordNotFoundException("El boleto no existe", e1);
			} catch (Exception e1) {
				logger.error("Exception in the method updateBoleto" + e1);
				throw new Exception("El boleto no existe");
			}
		} else {
			logger.error("Exception in the method updateBoleto");
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
			throws NullPointerException, RecordNotFoundException, IllegalArgumentException, Exception {
		if (id != null) {
			Optional<Boleto> deleteBoletoById;
			try {
				deleteBoletoById = Optional.ofNullable(getBoletoById(id));
				if (!deleteBoletoById.isEmpty()) {
					repository.deleteById(id);
				} else {
					logger.error("Exception in the method deleteBoletoById");
					throw new RecordNotFoundException("El boleto no existe", id);
				}
			} catch (IllegalArgumentException e1) {
				logger.error("Exception in the method deleteBoletoById" + e1);
				throw new IllegalArgumentException("El boleto no existe");
			} catch (NullPointerException e1) {
				logger.error("Exception in the method deleteBoletoById" + e1);
				throw new NullPointerException("El boleto no existe");
			} catch (RecordNotFoundException e1) {
				logger.error("Exception in the method deleteBoletoById" + e1);
				throw new RecordNotFoundException("El boleto no existe", e1);
			} catch (Exception e1) {
				logger.error("Exception in the method deleteBoletoById" + e1);
				throw new Exception("El boleto no existe");
			}
		} else {
			logger.error("Exception in the method deleteBoletoById");
			throw new NullPointerException("El id es un objeto nulo");
		}
	}

	/**
	 * Método que devuelve todas los boletos que han sido entregados, haciendo uso
	 * del repositorio del mismo
	 * 
	 * @return Lista con todos los boletos entregados
	 * @throws Exception
	 */
	public List<Boleto> getBoletosEntregados() throws Exception {
		try {
			List<Boleto> getBoletosEntregados = repository.getBoletosEntregados();
			return getBoletosEntregados;
		} catch (Exception e) {
			logger.error("Exception in the method getBoletosEntregados" + e);
			throw new Exception("El boleto no existe");
		}
	}

	/**
	 * Método que devuelve todas los boletos que han sido canjeados, haciendo uso
	 * del repositorio del mismo
	 * 
	 * @return Lista con todos los boletos canjeados
	 * @throws Exception
	 */
	public List<Boleto> getBoletosCanjeados() throws Exception {
		try {
			List<Boleto> getBoletosCanjeados = repository.getBoletosCanjeados();
			return getBoletosCanjeados;
		} catch (Exception e) {
			logger.error("Exception in the method getBoletosCanjeados" + e);
			throw new Exception("El boleto no existe");
		}
	}

	/**
	 * Método que devuelve todas los boletos de un usuario
	 * 
	 * @param id_usuario
	 * @return Lista con todos los boletos de un usuario en concreto
	 * @throws Exception, IllegalArgumentException
	 */
	public List<Boleto> getBoletosByIdComercio(Long id_usuario)
			throws Exception, IllegalArgumentException, NullPointerException {
		if (id_usuario != null) {
			try {
				List<Boleto> getBoletosByIdComercio = repository.getBoletosByIdComercio(id_usuario);
				return getBoletosByIdComercio;
			} catch (IllegalArgumentException e) {
				logger.error("Exception in the method getBoletosByIdComercio" + e);
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				logger.error("Exception in the method getBoletosByIdComercio" + e);
				throw new Exception(e);
			}
		} else {
			logger.error("Exception in the method getBoletosByIdComercio");
			throw new NullPointerException("El id es un objeto nulo");
		}
	}

	/**
	 * Actualiza un boleto con el parametro de entregado que le pasamos.
	 * 
	 * @param boleto    a actualizar
	 * @param entregado estado de entregado a actualizar
	 * @return true si lo actualiza bien, false si lo contrario.
	 * @throws IllegalArgumentException
	 */
	public boolean setBoletoEntregado(Boleto boleto, boolean entregado)
			throws IllegalArgumentException, NullPointerException {
		if (boleto != null) {
			Boleto setBoletoEntregado = repository.getOne(boleto.getId());
			setBoletoEntregado.setEntregado(entregado);
			try {
				repository.save(setBoletoEntregado);
				return true;
			} catch (IllegalArgumentException e) {
				logger.error("Exception in the method setBoletoEntregado" + e);
				return false;
			}
		} else {
			logger.error("Exception in the method setBoletoEntregado");
			throw new NullPointerException("El id es un objeto nulo");
		}
	}

	/**
	 * Método que devuelve una lista de boletos no entregado y distinta del comercio
	 * que va a realizar la peticion
	 * 
	 * @param id_usuario, id del comercio que no vamos a devolver los boletos.
	 * @return lista de boletos no entregados y de distinto comercio.
	 * @throws Exception
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public List<Boleto> getBoletosForRandomPick(Long id_usuario)
			throws Exception, IllegalArgumentException, NullPointerException {
		if (id_usuario != null && id_usuario > -1) {
			try {
				List<Boleto> getBoletosForRandomPick = repository.getBoletosForRandomPick(id_usuario);
				return getBoletosForRandomPick;
			} catch (IllegalArgumentException e) {
				logger.error("Exception in the method getBoletosForRandomPick" + e);
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				logger.error("Exception in the method getBoletosForRandomPick" + e);
				throw new Exception(e);
			}
		} else {
			logger.error("Exception in the method getBoletosForRandomPick id_usuario is null");
			throw new NullPointerException("El id es un objeto nulo");
		}
	}

	/**
	 * Método que obtiene un ticket de la base de datos buscando por el id de su
	 * boleto asociado. Si existe, lo devuelve, si no, lanza una excepción para
	 * dicho resultado
	 * 
	 * @param id_boleto
	 * @return ticket si lo encuentra, excepcion si no lo encuentra
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 */
	public Boleto getBoletoByIdPremio(int id_premio) throws RecordNotFoundException, NullPointerException, Exception {
		if (id_premio > -1) {
			try {
				Optional<Boleto> getBoletoByIdPremio = repository.getBoletoByIdPremio(id_premio);
				if (getBoletoByIdPremio.isPresent()) {
					return getBoletoByIdPremio.get();
				} else {
					logger.error("The premio doesn't exists in the database. ");
					throw new RecordNotFoundException("El premio asociado a ese boleto no existe", id_premio);
				}
			} catch (IllegalArgumentException e) {
				logger.error("The boleto doesn't exists in the database. " + e);
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				logger.error("The boleto doesn't exists in the database. " + e);
				throw new Exception("El ticket es un objeto nulo");
			}
		} else {
			logger.error("The boleto doesn't exists in the database. ");
			throw new NullPointerException("El id de boleto es un objeto nulo");
		}
	}

	public boolean checkPremios(Boleto boleto) {
		if (boleto != null) {
			if (boleto.getPremio() != null) {
				try {
					Boleto newBoleto = getBoletoByIdPremio(boleto.getPremio().getId());
					if (newBoleto != null && newBoleto.getId() > 0) {
						return false;
					} else {
						return true;
					}
				} catch (Exception e) {
					return true;
				}
			} else {
				return true;
				//logger.error("The boleto is null in checkTickets().");
				//throw new NullPointerException("The boleto is null in checkPremios().");
			}
		} else {
			logger.error("The ticket is null in checkTickets().");
			throw new NullPointerException("The ticket is null in checkPremios().");
		}
	}
}
