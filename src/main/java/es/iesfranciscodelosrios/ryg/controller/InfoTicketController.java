package es.iesfranciscodelosrios.ryg.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	@ApiOperation(value = "Encuentra todos los tickets", notes = "Devuelve una lista de todos los tickets")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Error obtener los tickets"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping
	public ResponseEntity<List<InfoTicket>> getAllTicket() {
		try {
			List<InfoTicket> all = service.getAllTicket();
			return new ResponseEntity<List<InfoTicket>>(all, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			List<InfoTicket> all = new ArrayList<InfoTicket>();
			return new ResponseEntity<List<InfoTicket>>(all, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver el ticket que le corresponda un id determinado
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Encuentra el ticket por su id", notes = "Devuelve un ticket con id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Id no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/{id}")
	public ResponseEntity<InfoTicket> getTicketById(@ApiParam("Ticket id (Long)") @PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				InfoTicket ticket = service.getInfoTicketById(id);
				return new ResponseEntity<InfoTicket>(ticket, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);

			}
		} else {
			return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver una lista de tickets filtrando por teléfono
	 * 
	 * @param telefono
	 * @return Lista de tickets de la base de datos según teléfono, lista vacía en
	 *         caso contrario
	 */
	@ApiOperation(value = "Encuentra todos los tickets asociados a un teléfono", notes = "Devuelve una lista de todos los tickets con teléfono")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Teléfono no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/telefono/{telefono}")
	public ResponseEntity<List<InfoTicket>> getTicketsByTelephone(@ApiParam("Ticket teléfono (int)") @PathVariable("telefono") int telefono) {
		try {
			List<InfoTicket> getTicketsByTelephone = service.getTicketsByTelephone(telefono);
			return new ResponseEntity<List<InfoTicket>>(getTicketsByTelephone, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			List<InfoTicket> getTicketsByTelephone = new ArrayList<InfoTicket>();
			return new ResponseEntity<List<InfoTicket>>(getTicketsByTelephone, new HttpHeaders(),
					HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver una lista de tickets filtrando por una fecha
	 * 
	 * @param fecha_ticket
	 * @return Ticket de la base de datos según id de boleto, vacío en caso
	 *         contrario
	 */
	@ApiOperation(value = "Encuentra todos los tickets asociados a una fecha", notes = "Devuelve una lista de todos los tickets de una fecha determinada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = List.class),
			@ApiResponse(code = 404, message = "No hay tickets con esa fecha"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/fecha/{fecha_ticket}")
	public ResponseEntity<List<InfoTicket>> getTicketsByDate(@ApiParam("Ticket date (LocalDate)") @PathVariable("fecha_ticket") String fecha_ticket) {
		if (fecha_ticket != null) {
			try {
				LocalDate fecha = LocalDate.parse(fecha_ticket, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				List<InfoTicket> getTicketsByDate = service.getTicketsByDate(fecha);
				return new ResponseEntity<List<InfoTicket>>(getTicketsByDate, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				List<InfoTicket> getTicketsByDate = new ArrayList<InfoTicket>();
				return new ResponseEntity<List<InfoTicket>>(getTicketsByDate, new HttpHeaders(),
						HttpStatus.BAD_REQUEST);
			}
		} else {
			List<InfoTicket> getTicketsByDate = new ArrayList<InfoTicket>();
			return new ResponseEntity<List<InfoTicket>>(getTicketsByDate, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver un ticket filtrando por su id de boleto
	 * 
	 * @param id_boleto
	 * @return Lista de tickets de la base de datos según fecha, lista vacía en caso
	 *         contrario
	 */
	@ApiOperation(value = "Encuentra el ticket por el id de su boleto", notes = "Devuelve un ticket con id de su boleto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Id boleto no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/boleto/{id_boleto}")
	public ResponseEntity<InfoTicket> getTicketByIdBoleto(@ApiParam("Ticket id boleto (Long)") @PathVariable("id_boleto") Long id_boleto) {
		if (id_boleto != null && id_boleto > -1) {
			try {
				InfoTicket getTicketByIdBoleto = service.getTicketByIdBoleto(id_boleto);
				return new ResponseEntity<InfoTicket>(getTicketByIdBoleto, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y crear un ticket nuevo
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Crea un nuevo ticket", notes = "Crea un nuevo ticket siempre que tenga un id válido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Error al crear el ticket"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
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
	@ApiOperation(value = "Edita un ticket", notes = "Edita un ticket siempre que tenga un id válido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Error al editar el ticket"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
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
	@ApiOperation(value = "Borra un ticket", notes = "Borra un ticket usando id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Id no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@DeleteMapping("/{id}")
	public HttpStatus deleteTicketById(@ApiParam("Ticket id (Long)") @PathVariable("id") Long id) {
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
