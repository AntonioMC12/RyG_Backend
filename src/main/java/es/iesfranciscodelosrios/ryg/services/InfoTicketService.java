package es.iesfranciscodelosrios.ryg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.InfoTicket;
import es.iesfranciscodelosrios.ryg.repository.InfoTicketRepository;

@Service
public class InfoTicketService {

	@Autowired
	InfoTicketRepository repository;

	/**
	 * Método que devuelve todos los tickets, haciendo uso del repositorio del mismo
	 * 
	 * @return Lista con todos los tickets existentes
	 */
	public List<InfoTicket> getAllTicket() {
		List<InfoTicket> result = repository.findAll();
		return result;
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
	public InfoTicket getInfoTicketById(Long id) throws RecordNotFoundException, NullPointerException {
		if (id != null) {
			try {
				Optional<InfoTicket> result = repository.findById(id); 
				if (result.isPresent()) {
					return result.get();
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
	 * Método que crea un ticket en la base de datos, si existe el objeto
	 * referenciado. Si el objeto ya estaba persistido, se ejecuta el método
	 * de actualizar
	 * @param ticket
	 * @return Ticket creado en la base de datos 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public InfoTicket createInfoTicket(InfoTicket ticket) throws NullPointerException, IllegalArgumentException{
		if(ticket != null) {
			if(ticket.getId() !=-1) {
				ticket.setId(null);
				try {
					return ticket = repository.save(ticket);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			}else {
				return updateInfoTicket(ticket);
			}
		}else {
			throw new NullPointerException("El ticket es un objeto nulo");
		}
	}
	
	/**
	 * Método que actualiza la información de un ticket de la base de datos
	 * @param ticket
	 * @return ticket actualizado
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws RecordNotFoundException
	 */
	public InfoTicket updateInfoTicket(InfoTicket ticket) throws NullPointerException, IllegalArgumentException, RecordNotFoundException{
		if(ticket != null) {
			Optional<InfoTicket> getTicket = Optional.ofNullable(getInfoTicketById(ticket.getId()));
			if(!getTicket.isEmpty()) {
				InfoTicket newTicket = getTicket.get();
				newTicket.setId(ticket.getId());
				newTicket.setFechaTicket(ticket.getFechaTicket());
				newTicket.setNombreCliente(ticket.getNombreCliente());
				newTicket.setNombreComercio(ticket.getNombreComercio());
				newTicket.setNumeroTicket(ticket.getNumeroTicket());
				newTicket.setTelefono(ticket.getTelefono());
				try {
					return repository.save(newTicket);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			}else {
				throw new RecordNotFoundException("El ticket no existe", ticket.getId());
			}
		}else {
			throw new NullPointerException("El ticket es un objeto nulo");
		}
	}



	/**
	 * Método que borra un ticket de la base de datos, si no lo encuentra
	 * lanza una excepción
	 * @param id
	 * @throws RecordNotFoundException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public void deleteInfoTicketById(Long id) throws RecordNotFoundException, NullPointerException, IllegalArgumentException {
		if(id!=null) {
			Optional<InfoTicket> ticket = Optional.ofNullable(getInfoTicketById(id));
			if(!ticket.isEmpty()) {
				try {
					repository.deleteById(id);
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(e);
				}
			}else {
				throw new RecordNotFoundException("El ticket no existe", id);
			}
		}else {
			throw new NullPointerException("El id es un objeto nulo");
		}
		
	}
}
