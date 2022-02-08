package es.iesfranciscodelosrios.ryg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
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

import es.iesfranciscodelosrios.ryg.model.Boleto;
import es.iesfranciscodelosrios.ryg.services.BoletoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

	@Autowired
	BoletoService service;

	/**
	 * M�todo que recoge una petici�n http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Boletos.
	 * 
	 * @return Lista con todos los boletos de la base de datos, una lista vac�a si
	 *         algo ha ido mal
	 */
	@ApiOperation(value = "Encuentra todos los boletos", notes = "Devuelve una lista de todos los boletos")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci�n exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Error al obtener los boletos"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping
	public ResponseEntity<List<Boleto>> getAllBoletos() {
		try {
			List<Boleto> getAllBoletos = service.getAllBoleto();
			return new ResponseEntity<List<Boleto>>(getAllBoletos, new HttpHeaders(), HttpStatus.OK);

		} catch (Exception e) {
			List<Boleto> getAllBoletos = new ArrayList<Boleto>();
			return new ResponseEntity<List<Boleto>>(getAllBoletos, new HttpHeaders(), HttpStatus.OK);
		}
	}

	/**
	 * M�todo que recoge una petici�n http a nuestra API para hacer una consulta a
	 * la base de datos y obtener el boleto con el id que pasamos por par�metro
	 * 
	 * @param id del boleto a buscar
	 * @return Boleto encontrado, o boleto vac�o si hay alg�n error.
	 */
	@ApiOperation(value = "Encuentra el boleto por su id", notes = "Devuelve un boleto con id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci�n exitosa", response = Boleto.class),
			@ApiResponse(code = 404, message = "Id no v�lido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/{id}")
	public ResponseEntity<Boleto> getBoletoById(@ApiParam("Boleto id (Long)") @PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				Boleto getBoletoById = service.getBoletoById(id);
				return new ResponseEntity<Boleto>(getBoletoById, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Boleto>(new Boleto(), new HttpHeaders(), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Boleto>(new Boleto(), new HttpHeaders(), HttpStatus.OK);
		}
	}

	/**
	 * M�todo que recoge una petici�n http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Boletos de un usuario en concreto.
	 * 
	 * @return Lista con todos los boletos de un usuario de la base de datos, una
	 *         lista vac�a si algo ha ido mal
	 */
	@ApiOperation(value = "Encuentra todos los boletos de un comercio", notes = "Devuelve una lista de todos los boletos con id del comercio")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci�n exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Id comercio no v�lido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<List<Boleto>> getBoletosByIdComercio(
			@ApiParam("Comercio id (Long)") @PathVariable("id_comercio") Long id_comercio) {
		if (id_comercio != null && id_comercio > -1) {
			try {
				List<Boleto> getBoletosByIdComercio = service.getBoletosByIdComercio(id_comercio);
				return new ResponseEntity<List<Boleto>>(getBoletosByIdComercio, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				List<Boleto> getBoletosByIdComercio = new ArrayList<Boleto>();
				return new ResponseEntity<List<Boleto>>(getBoletosByIdComercio, new HttpHeaders(), HttpStatus.OK);
			}
		} else {
			List<Boleto> getBoletosByIdComercio = new ArrayList<Boleto>();
			return new ResponseEntity<List<Boleto>>(getBoletosByIdComercio, new HttpHeaders(), HttpStatus.OK);
		}
	}

	/**
	 * M�todo que recoge una petici�n http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Boletos entregados.
	 * 
	 * @return Lista con todos los boletos entregados de la base de datos, una lista
	 *         vac�a si algo ha ido mal
	 */
	@ApiOperation(value = "Encuentra todos los boletos entregados", notes = "Devuelve una lista de todos los boletos que han sido entregados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci�n exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Entregado no v�lido/no hay boletos entregados"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/entregados")
	public ResponseEntity<List<Boleto>> getBoletosEntregados(
			@ApiParam("entregado (boolean)") @PathVariable("entregado") boolean entregado) {
		if (entregado != false) {
			try {
				List<Boleto> getBoletosEntregados = service.getBoletosEntregados();
				return new ResponseEntity<List<Boleto>>(getBoletosEntregados, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				List<Boleto> getBoletosEntregados = new ArrayList<Boleto>();
				return new ResponseEntity<List<Boleto>>(getBoletosEntregados, new HttpHeaders(), HttpStatus.OK);
			}
		} else {
			List<Boleto> getBoletosEntregados = new ArrayList<Boleto>();
			return new ResponseEntity<List<Boleto>>(getBoletosEntregados, new HttpHeaders(), HttpStatus.OK);
		}

	}

	/**
	 * M�todo que recoge una petici�n http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Boletos canjeados.
	 * 
	 * @return Lista con todos los boletos canjeados de la base de datos, una lista
	 *         vac�a si algo ha ido mal
	 */
	@ApiOperation(value = "Encuentra todos los boletos canjeados", notes = "Devuelve una lista de todos los boletos que han sido canjeados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci�n exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Canjeado no v�lido/no hay boletos canjeados"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/canjeados")
	public ResponseEntity<List<Boleto>> getBoletosCanjeados(
			@ApiParam("canjeado (boolean)") @PathVariable("canjeado") boolean canjeado) {
		if (canjeado != false) {
			try {
				List<Boleto> getBoletosCanjeados = service.getBoletosCanjeados();
				return new ResponseEntity<List<Boleto>>(getBoletosCanjeados, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				List<Boleto> getBoletosCanjeados = new ArrayList<Boleto>();
				return new ResponseEntity<List<Boleto>>(getBoletosCanjeados, new HttpHeaders(), HttpStatus.OK);
			}
		} else {
			List<Boleto> getBoletosCanjeados = new ArrayList<Boleto>();
			return new ResponseEntity<List<Boleto>>(getBoletosCanjeados, new HttpHeaders(), HttpStatus.OK);
		}

	}

	/**
	 * M�todo que recoge una petici�n http a nuestra API para hacer una consulta a
	 * la base de datos y a�adir un boleto.
	 * 
	 * @param boleto a a�adir
	 * @return boleto con el id a�adido o boleto vac�o si ha ido mal.
	 */
	@ApiOperation(value = "Crea un nuevo boleto", notes = "Crea un nuevo boleto siempre que tenga un id v�lido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci�n exitosa", response = Boleto.class),
			@ApiResponse(code = 404, message = "Error al crear el boleto"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping
	public ResponseEntity<Boleto> createBoleto(@Valid @RequestBody Boleto boleto) {
		if (boleto != null && boleto.getId() == -1) {
			try {
				Boleto createBoleto = service.createBoleto(boleto);
				return new ResponseEntity<Boleto>(createBoleto, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Boleto>(new Boleto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Boleto>(new Boleto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * M�todo que recoge una petici�n http a nuestra API para hacer una consulta a
	 * la base de datos y actualilzar un boleto.
	 * 
	 * @param boleto a a�adir
	 * @return boleto actualizado o boleto vac�o si ha ido mal.
	 */
	@ApiOperation(value = "Edita un boleto", notes = "Edita un boleto siempre que tenga un id v�lido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci�n exitosa", response = Boleto.class),
			@ApiResponse(code = 404, message = "Error al editar el boleto"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PutMapping
	public ResponseEntity<Boleto> updateBoleto(@Valid @RequestBody Boleto boleto) {
		if (boleto != null && boleto.getId() != -1) {
			try {
				Boleto createBoleto = service.updateBoleto(boleto);
				return new ResponseEntity<Boleto>(createBoleto, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Boleto>(new Boleto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Boleto>(new Boleto(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * M�todo para borrar un boleto de la base de datos por su id
	 * 
	 * @param id del boleto a borrar
	 * @return Ok si lo borra, Bad_Request.
	 */
	@ApiOperation(value = "Borra un boleto", notes = "Borra un boleto usando id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operaci�n exitosa", response = Boleto.class),
			@ApiResponse(code = 404, message = "Id no v�lido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/{id}")
	public HttpStatus deleteBoletoById(@ApiParam("Boleto id (Long)") @PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				service.deleteBoletoById(id);
			} catch (Exception e) {
				return HttpStatus.BAD_REQUEST;
			}
			return HttpStatus.OK;
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}
}
