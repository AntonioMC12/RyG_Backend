package es.iesfranciscodelosrios.ryg.controller;

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

import es.iesfranciscodelosrios.ryg.model.Usuario;
import es.iesfranciscodelosrios.ryg.services.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService service;

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver una lista de usuarios
	 * 
	 * @return Lista de usuarios de la base de datos, lista vacía en caso contrario
	 */
	@ApiOperation(value = "Encuentra todos los usuarios", notes = "Devuelve una lista de todos los usuarios")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = List.class),
			@ApiResponse(code = 404, message = "Error al obtener los usuarios"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuarios() {
		try {
			List<Usuario> getAllUsuarios = service.getAllUsuario();
			return new ResponseEntity<List<Usuario>>(getAllUsuarios, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			List<Usuario> getAllUsuarios = new ArrayList<Usuario>();
			return new ResponseEntity<List<Usuario>>(getAllUsuarios, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver el usuario que le corresponda un id determinado
	 * 
	 * @param id
	 * @return Usuario encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Encuentra el usuario por su id", notes = "Devuelve un usuario con id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Usuario.class),
			@ApiResponse(code = 404, message = "Id no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@ApiParam("Usuario id (Long)") @PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				Usuario getUsuarioById = service.getUsuarioById(id);
				return new ResponseEntity<Usuario>(getUsuarioById, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver el usuario que le corresponda una latitud y longitud
	 * determinadas
	 * 
	 * @param latitud
	 * @param longitud
	 * @return Usuario encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Encuentra un usuario por su latitud y longitud", notes = "Devuelve un usuario según sus coordenadas (latitud y longitud)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Usuario.class),
			@ApiResponse(code = 404, message = "Coordenadas no válidas"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@GetMapping("/coordenadas/{latitud}/{longitud}")
	public ResponseEntity<Usuario> getUsuarioByCoordinates(@ApiParam("Usuario latitud (float)") @PathVariable("latitud") float latitud,
			@ApiParam("Usuario longitud (float)") @PathVariable("longitud") float longitud) {
		Usuario getUsuarioByCoordinates;
		try {
			getUsuarioByCoordinates = service.getUsuarioByCoordinates(latitud, longitud);
			return new ResponseEntity<Usuario>(getUsuarioByCoordinates, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y crear un usuario nuevo
	 * 
	 * @param id
	 * @return Usuario encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Crea un nuevo usuario", notes = "Crea un nuevo usuario siempre que tenga un id válido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Usuario.class),
			@ApiResponse(code = 404, message = "Error al crear el usuario"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
		if (usuario != null && usuario.getId() == -1) {
			try {
				Usuario createUsuario = service.createUsuario(usuario);
				return new ResponseEntity<Usuario>(createUsuario, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.BAD_GATEWAY);
			}
		} else {
			return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y actualizar un usuario
	 * 
	 * @param id
	 * @return Usuario encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Edita un usuario", notes = "Edita un usuario siempre que tenga un id válido")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Usuario.class),
			@ApiResponse(code = 404, message = "Error al editar el usuario"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@PutMapping
	public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario) {
		if (usuario != null && usuario.getId() != -1) {
			try {
				Usuario createUsuario = service.updateUsuario(usuario);
				return new ResponseEntity<Usuario>(createUsuario, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y borrar un usuario
	 * 
	 * @param id del usuario a borrar
	 * @return Usuario encontrado en la bd, o vacío si hay algún error
	 */
	@ApiOperation(value = "Borra un usuario", notes = "Borra un usuario usando id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Operación exitosa", response = Usuario.class),
			@ApiResponse(code = 404, message = "Id no válido"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@CrossOrigin(origins = "http://localhost:8100")
	@DeleteMapping("/{id}")
	public HttpStatus deleteUsuarioById(@ApiParam("Usuario id (Long)") @PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				service.deleteUsuarioById(id);
			} catch (Exception e) {
				return HttpStatus.BAD_REQUEST;
			}
			return HttpStatus.OK;
		} else {
			return HttpStatus.BAD_REQUEST;
		}
	}
}
