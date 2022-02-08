package es.iesfranciscodelosrios.ryg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesfranciscodelosrios.ryg.model.Premio;
import es.iesfranciscodelosrios.ryg.services.PremioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/premios")

public class PremioController {
	@Autowired
	PremioService service;

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver una lista de premios
	 * 
	 * @return Lista de premios de la base de datos, lista vacía en caso contrario
	 */
	@ApiOperation(value = "Encuentra todos los premios", notes = "Devuelve una lista de todos los premios")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Error al obtener los premios"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping
	public ResponseEntity<List<Premio>> getAllPremios() {
		try {
			List<Premio> list = service.getAllPremios();
			return new ResponseEntity<List<Premio>>(list, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			List<Premio> list = new ArrayList<Premio>();
			return new ResponseEntity<List<Premio>>(list, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver el premio que le corresponda un id determinado
	 * 
	 * @param id
	 * @return premio encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Encuentra el premio por su id", notes = "Devuelve un premio con id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Boleto.class),
			@ApiResponse(code = 404, message = "Id no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/{id}")
	public ResponseEntity<Premio> getPremiosById(@ApiParam("Boleto id (Integer)") @PathVariable("id") Integer id) {
		if (id != null && id > -1) {
			try {
				Premio entity = service.getPremiosById(id);
				return new ResponseEntity<Premio>(entity, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Premio>(new Premio(), new HttpHeaders(), HttpStatus.BAD_REQUEST);

			}
		} else {
			return new ResponseEntity<Premio>(new Premio(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Encuentra premios por su descripción", notes = "Devuelve una lista de premios con descripción")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Id no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/description/{description}")
	public ResponseEntity<List<Premio>> getPremiosByDescription(
			@ApiParam("Premio descripcion (String)") @PathVariable("description") String description) {
		List<Premio> list;
		try {
			list = service.getPremiosByDescription(description);
			return new ResponseEntity<List<Premio>>(list, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Premio>>(new ArrayList<Premio>(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Premios entregados.
	 * 
	 * @return Lista con todos los premios entregados de la base de datos, una lista
	 *         vacía si algo ha ido mal
	 */
	@ApiOperation(value = "Encuentra premios por su entregado", notes = "Devuelve la lista de premios que han sido entregados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Entregado no válido/no hay premios entregados"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/entregado")
	public ResponseEntity<List<Premio>> getPremiosEntregados() {
			try {
				List<Premio> getPremiosEntregados = service.getPremiosEntregados();
				return new ResponseEntity<List<Premio>>(getPremiosEntregados, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				List<Premio> getPremiosEntregados = new ArrayList<Premio>();
				return new ResponseEntity<List<Premio>>(getPremiosEntregados, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		

	}

	/**
	 * Método que recoge una petición http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Premios no entregados.
	 * 
	 * @return Lista con todos los premios no entregados de la base de datos, una
	 *         lista vacía si algo ha ido mal
	 */
	@ApiOperation(value = "Encuentra premios por su no-entregado", notes = "Devuelve la lista de premios que no han sido entregados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = List.class),
			@ApiResponse(code = 404, message = "No entregado no válido/no hay premios no entregados"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@GetMapping("/no-entregado")
	public ResponseEntity<List<Premio>> getPremiosNoEntregados() {
			try {
				List<Premio> getPremiosNoEntregados = service.getPremiosNoEntregados();
				return new ResponseEntity<List<Premio>>(getPremiosNoEntregados, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				List<Premio> getPremiosNoEntregados = new ArrayList<Premio>();
				return new ResponseEntity<List<Premio>>(getPremiosNoEntregados, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
	

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y crear un premio nuevo
	 * 
	 * @param id
	 * @return premio encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Crea un nuevo premio", notes = "Crea un nuevo premio siempre que tenga un id válido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Premio.class),
			@ApiResponse(code = 404, message = "Error al crear el premio"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping
	public ResponseEntity<Premio> createPremios(@Valid @RequestBody Premio myPremio) {
		if (myPremio != null && myPremio.getId() == -1) {
			try {
				Premio created = service.createPremio(myPremio);
				return new ResponseEntity<Premio>(created, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Premio>(new Premio(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Premio>(new Premio(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y actualizar un premio
	 * 
	 * @param id
	 * @return premio encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Edita un premio", notes = "Edita un premio siempre que tenga un id válido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Premio.class),
			@ApiResponse(code = 404, message = "Error al editar el premio"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PutMapping
	public ResponseEntity<Premio> updatePremio(@Valid @RequestBody Premio myPremio) {
		if (myPremio != null && myPremio.getId() != -1) {
			try {
				Premio updated = service.updatePremio(myPremio);
				return new ResponseEntity<Premio>(updated, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Premio>(new Premio(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Premio>(new Premio(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y borrar un premio
	 * 
	 * @param id
	 * @return premio encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Borra un premio", notes = "Borra un premio usando id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Premio.class),
			@ApiResponse(code = 404, message = "Id no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@DeleteMapping("/{id}")
	public HttpStatus deletePremioById(@ApiParam("Premio id (Long)") @PathVariable("id") Integer id) {
		if (id != null && id > -1) {
			try {
				service.deletePremioById(id);
			} catch (Exception e) {
				return HttpStatus.BAD_REQUEST;
			}
			return HttpStatus.OK;
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}

}