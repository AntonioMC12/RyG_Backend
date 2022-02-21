package es.iesfranciscodelosrios.ryg.controller;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesfranciscodelosrios.ryg.model.InfoTicket;
import es.iesfranciscodelosrios.ryg.services.CloudinaryService;
import es.iesfranciscodelosrios.ryg.services.InfoTicketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/tickets")

public class InfoTicketController {

	@Autowired
	CloudinaryService cluCloudinaryService;

	@Autowired
	InfoTicketService service;

	/**
	 * M_todo que recoge una petici_n http para hacer una consulta a la base de
	 * datos y devolver una lista de tickets
	 * 
	 * @return Lista de tickets de la base de datos, lista vac_a en caso contrario
	 */
	@ApiOperation(value = "Encuentra todos los tickets", notes = "Devuelve una lista de todos los tickets")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci_n exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Error obtener los tickets"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping
	public ResponseEntity<List<InfoTicket>> getAllTicket() {
		try {
			List<InfoTicket> all = service.getAllTicket();
			return new ResponseEntity<List<InfoTicket>>(all, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			List<InfoTicket> all = new ArrayList<InfoTicket>();
			return new ResponseEntity<List<InfoTicket>>(all, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * M_todo que recoge una petici_n http para hacer una consulta a la base de
	 * datos y devolver el ticket que le corresponda un id determinado
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vac_o si hay alg_n error
	 */
	@ApiOperation(value = "Encuentra el ticket por su id", notes = "Devuelve un ticket con id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci_n exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Id no v_lido"),
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
	 * M_todo que recoge una petici_n http para hacer una consulta a la base de
	 * datos y devolver una lista de tickets filtrando por tel_fono
	 * 
	 * @param telefono
	 * @return Lista de tickets de la base de datos seg_n tel_fono, lista vac_a en
	 *         caso contrario
	 */
	@ApiOperation(value = "Encuentra todos los tickets asociados a un tel_fono", notes = "Devuelve una lista de todos los tickets con tel_fono")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci_n exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Tel_fono no v_lido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/telefono/{telefono}")
	public ResponseEntity<List<InfoTicket>> getTicketsByTelephone(
			@ApiParam("Ticket tel_fono (int)") @PathVariable("telefono") int telefono) {
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
	 * M_todo que recoge una petici_n http para hacer una consulta a la base de
	 * datos y devolver una lista de tickets filtrando por una fecha
	 * 
	 * @param fecha_ticket
	 * @return Ticket de la base de datos seg_n id de boleto, vac_o en caso
	 *         contrario
	 */
	@ApiOperation(value = "Encuentra todos los tickets asociados a una fecha", notes = "Devuelve una lista de todos los tickets de una fecha determinada")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci_n exitosa", response = List.class),
			@ApiResponse(code = 404, message = "No hay tickets con esa fecha"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/fecha/{fecha_ticket}")
	public ResponseEntity<List<InfoTicket>> getTicketsByDate(
			@ApiParam("Ticket date (LocalDate)") @PathVariable("fecha_ticket") String fecha_ticket) {
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
	 * M_todo que recoge una petici_n http para hacer una consulta a la base de
	 * datos y devolver un ticket filtrando por su id de boleto
	 * 
	 * @param id_boleto
	 * @return Lista de tickets de la base de datos seg_n fecha, lista vac_a en caso
	 *         contrario
	 */
	@ApiOperation(value = "Encuentra el ticket por el id de su boleto", notes = "Devuelve un ticket con id de su boleto")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci_n exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Id boleto no v_lido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/boleto/{id_boleto}")
	public ResponseEntity<InfoTicket> getTicketByIdBoleto(
			@ApiParam("Ticket id boleto (Long)") @PathVariable("id_boleto") Long id_boleto) {
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
	 * M_todo para insertar un ticket en la base de datos, recibiendo los datos del
	 * ticket en JSON y la foto como MultipartFile guardando el contenido multimedia
	 * en Cloudinary y almacenando la url en la base de datos.
	 * 
	 * @param ticket        JSON con la informaci_n del ticket
	 * @param multipartFile archivo multimedia para insertar en cloudinary y guardar
	 *                      la url en maridaDB.
	 * @return JSON con la informaci_n del ticket en la base de datos o ticket vac_o
	 *         si hay error.
	 */
	@ApiOperation(value = "Crea un nuevo ticket", notes = "Crea un nuevo ticket siempre que tenga un id v_lido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci_n exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Error al crear el ticket"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@RequestMapping(path = "", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<InfoTicket> createTicket(@Valid @RequestPart InfoTicket ticket,
			@Valid @RequestPart MultipartFile multipartFile) {
		if (ticket != null && multipartFile != null && ticket.getId() == -1) {
			try {
				//Obtengo la imagen, leyendo del Stream para subirla a cloudinary.
				BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
				if (bi == null) {
					return new ResponseEntity<InfoTicket>(new InfoTicket(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
				}
				//Subo la foto a cludinary y obtengo su url para guardarla en la base de datos.
				Map<?, ?> result = cluCloudinaryService.upload(multipartFile);
				ticket.setFoto((String) result.get("url"));
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
	 * M_todo que recoge una petici_n http para hacer una consulta a la base de
	 * datos y actualizar un ticket
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vac_o si hay alg_n error
	 */
	@ApiOperation(value = "Edita un ticket", notes = "Edita un ticket siempre que tenga un id v_lido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci_n exitosa", response = InfoTicket.class),
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
	 * M_todo que recoge una petici_n http para hacer una consulta a la base de
	 * datos y borrar un ticket
	 * 
	 * @param id
	 * @return Ticket encontrado en la bd, o vac_o si hay alg_n error
	 */
	@ApiOperation(value = "Borra un ticket", notes = "Borra un ticket usando id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci_n exitosa", response = InfoTicket.class),
			@ApiResponse(code = 404, message = "Id no v_lido"),
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
