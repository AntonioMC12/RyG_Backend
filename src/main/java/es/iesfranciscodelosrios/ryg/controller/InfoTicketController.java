package es.iesfranciscodelosrios.ryg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.iesfranciscodelosrios.ryg.model.InfoTicket;
import es.iesfranciscodelosrios.ryg.services.InfoTicketService;

@RestController
@RequestMapping("/tickets")

public class InfoTicketController {

	@Autowired
	InfoTicketService service;

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver una lista de tickets
	 * 
	 * @return Lista de tickets de la base de datos, lista vacía en caso contrario
	 */
	@GetMapping
	public ResponseEntity<List<InfoTicket>> getAllTicket() {
		try {
			List<InfoTicket> all = service.getAllTicket();
			return new ResponseEntity<List<InfoTicket>>(all, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			List<InfoTicket> all = new ArrayList<InfoTicket>();
			return new ResponseEntity<List<InfoTicket>>(all, new HttpHeaders(), HttpStatus.OK);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver el ticket que le corresponda un id determinado
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vacío si hay algún error
	 */
	@GetMapping("/{id}")
	public ResponseEntity<InfoTicket> getTicketById(@PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				InfoTicket ticket = service.getInfoTicketById(id);
				return new ResponseEntity<InfoTicket>(ticket, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.OK);

			}
		} else {
			return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.OK);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y crear un ticket nuevo
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vacío si hay algún error
	 */
	@PostMapping
	public ResponseEntity<InfoTicket> createTicket(@Valid @RequestBody InfoTicket ticket) {
		if (ticket != null && ticket.getId() == -1) {
			try {
				InfoTicket newTicket = service.createInfoTicket(ticket);
				return new ResponseEntity<InfoTicket>(newTicket, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y actualizar un ticket
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vacío si hay algún error
	 */
	@PutMapping
	public ResponseEntity<InfoTicket> updateTicket(@Valid @RequestBody InfoTicket ticket) {
		if (ticket != null && ticket.getId() != -1) {
			try {
				InfoTicket newTicket = service.updateInfoTicket(ticket);
				return new ResponseEntity<InfoTicket>(newTicket, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y borrar un ticket
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vacío si hay algún error
	 */
	@DeleteMapping("/{id}")
	public HttpStatus deleteTicketById(@PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				service.deleteInfoTicketById(id);
			} catch (Exception e) {
				return HttpStatus.BAD_REQUEST;
			}
			return HttpStatus.OK;
		} else {
			return HttpStatus.BAD_REQUEST;
		}

	}

}
