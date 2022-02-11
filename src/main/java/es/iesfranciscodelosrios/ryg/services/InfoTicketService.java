package es.iesfranciscodelosrios.ryg.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.InfoTicket;
import es.iesfranciscodelosrios.ryg.repository.InfoTicketRepository;

@Service
public class InfoTicketService {

	@Autowired
	InfoTicketRepository repository;

	private static final Logger logger = LogManager.getLogger("InfoTicketService");

	/**
	 * Método que devuelve todos los tickets, haciendo uso del repositorio del mismo
	 * 
	 * @return Lista con todos los tickets existentes
	 * @throws Exception
	 */
	public List<InfoTicket> getAllTicket() throws Exception {
		try {
			List<InfoTicket> result = repository.findAll();
			return result;
		} catch (Exception e) {
			logger.error("The boleto doesn't exists in the database." + e);
			throw new Exception("El boleto no existe", e);
		}
	}

	/**
	 * Método que obtiene un ticket de la base de datos buscando por su id, si
	 * existe, lo devuelve, si no, lanza una excepción para dicho resultado
	 * 
	 * @param id
	 * @return ticket si lo encuentra, excepcion si no lo encuentra
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 */
	public InfoTicket getInfoTicketById(Long id) throws RecordNotFoundException, NullPointerException, Exception {
		if (id != null) {
			try {
				Optional<InfoTicket> result = repository.findById(id);
				if (result.isPresent()) {
					return result.get();
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("La nota no existe", id);
				}
			} catch (IllegalArgumentException e) {
				logger.error("The boleto doesn't exists in the database." + e);
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				logger.error("The boleto doesn't exists in the database." + e);
				throw new Exception(e);
			}

		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El id es un objeto nulo");
		}

	}

	/**
	 * Método que crea un ticket en la base de datos, si existe el objeto
	 * referenciado. Si el objeto ya estaba persistido, se ejecuta el método de
	 * actualizar
	 * 
	 * @param ticket
	 * @return Ticket creado en la base de datos
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public InfoTicket createInfoTicket(InfoTicket ticket)
			throws NullPointerException, IllegalArgumentException, Exception {
		if (ticket != null) {
			if (ticket.getId() == -1) {
				ticket.setId(null);
				try {
					return ticket = repository.save(ticket);
				} catch (IllegalArgumentException e) {
					logger.error("The boleto doesn't exists in the database." + e);
					throw new IllegalArgumentException(e);
				} catch (Exception e) {
					logger.error("The boleto doesn't exists in the database." + e);
					throw new Exception(e);
				}
			} else {
				return updateInfoTicket(ticket);
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El ticket es un objeto nulo");
		}
	}

	/**
	 * Método que actualiza la información de un ticket de la base de datos
	 * 
	 * @param ticket
	 * @return ticket actualizado
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws RecordNotFoundException
	 */
	public InfoTicket updateInfoTicket(InfoTicket ticket)
			throws NullPointerException, IllegalArgumentException, RecordNotFoundException, Exception {
		if (ticket != null) {
			Optional<InfoTicket> getTicket;
			try {
				getTicket = Optional.ofNullable(getInfoTicketById(ticket.getId()));
				if (!getTicket.isEmpty()) {
					InfoTicket newTicket = getTicket.get();
					newTicket.setId(ticket.getId());
					newTicket.setFechaTicket(ticket.getFechaTicket());
					newTicket.setNombreCliente(ticket.getNombreCliente());
					newTicket.setNombreComercio(ticket.getNombreComercio());
					newTicket.setNumeroTicket(ticket.getNumeroTicket());
					newTicket.setTelefono(ticket.getTelefono());
					return repository.save(newTicket);
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("El ticket no existe", ticket.getId());
				}
			} catch (RecordNotFoundException e1) {
				logger.error("The boleto doesn't exists in the database. " + e1);
				throw new RecordNotFoundException("El ticket no existe", ticket.getId());
			} catch (NullPointerException e1) {
				logger.error("The boleto doesn't exists in the database. " + e1);
				throw new NullPointerException("El ticket es un objeto nulo");
			} catch (Exception e1) {
				logger.error("The boleto doesn't exists in the database. " + e1);
				throw new Exception("El ticket es un objeto nulo");
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El ticket es un objeto nulo");
		}
	}

	/**
	 * Método que borra un ticket de la base de datos, si no lo encuentra lanza una
	 * excepción
	 * 
	 * @param id
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void deleteInfoTicketById(Long id)
			throws RecordNotFoundException, NullPointerException, IllegalArgumentException, Exception {
		if (id != null) {
			Optional<InfoTicket> ticket;
			try {
				ticket = Optional.ofNullable(getInfoTicketById(id));
				if (!ticket.isEmpty()) {
					repository.deleteById(id);
				} else {
					logger.error("The boleto doesn't exists in the database.");
					throw new RecordNotFoundException("El ticket no existe", id);
				}
			} catch (RecordNotFoundException e1) {
				logger.error("The boleto doesn't exists in the database. " + e1);
				throw new RecordNotFoundException("El ticket es un objeto nulo", e1);
			} catch (NullPointerException e1) {
				logger.error("The boleto doesn't exists in the database. " + e1);
				throw new NullPointerException("El ticket es un objeto nulo");
			} catch (Exception e1) {
				logger.error("The boleto doesn't exists in the database. " + e1);
				throw new Exception("El ticket es un objeto nulo");
			}

		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("El id es un objeto nulo");
		}

	}

	/**
	 * Método que devuelve todos los tickets de un teléfono
	 * 
	 * @param telefono
	 * @return Lista con todos los tickets de un teléfono en concreto
	 * @throws IllegalArgumentException
	 */
	public List<InfoTicket> getTicketsByTelephone(int telefono) throws IllegalArgumentException, Exception {
		try {
			List<InfoTicket> getTicketsByTelephone = repository.getTicketsByTelephone(telefono);
			return getTicketsByTelephone;
		} catch (IllegalArgumentException e) {
			logger.error("The boleto doesn't exists in the database. " + e);
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			logger.error("The boleto doesn't exists in the database. " + e);
			throw new Exception("El ticket es un objeto nulo");
		}
	}

	/**
	 * Método que devuelve todos los tickets de una fecha concreta
	 * 
	 * @param fecha_ticket
	 * @return Lista con todos los tickets de una fecha
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public List<InfoTicket> getTicketsByDate(LocalDate fecha_ticket)
			throws NullPointerException, IllegalArgumentException, Exception {
		if (fecha_ticket != null) {
			try {
				List<InfoTicket> getTicketsByDate = repository.getTicketsByDate(fecha_ticket);
				return getTicketsByDate;
			} catch (IllegalArgumentException e) {
				logger.error("The boleto doesn't exists in the database. " + e);
				throw new IllegalArgumentException(e);
			} catch (Exception e) {
				logger.error("The boleto doesn't exists in the database. " + e);
				throw new Exception("El ticket es un objeto nulo");
			}
		} else {
			logger.error("The boleto doesn't exists in the database.");
			throw new NullPointerException("La fecha es nula");
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
	public InfoTicket getTicketByIdBoleto(Long id_boleto)
			throws RecordNotFoundException, NullPointerException, Exception {
		if (id_boleto != null) {
			try {
				Optional<InfoTicket> getTicketByIdBoleto = repository.getTicketByIdBoleto(id_boleto);
				if (getTicketByIdBoleto.isPresent()) {
					return getTicketByIdBoleto.get();
				} else {
					logger.error("The boleto doesn't exists in the database. ");
					throw new RecordNotFoundException("El ticket asociado a ese boleto no existe", id_boleto);
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
}
