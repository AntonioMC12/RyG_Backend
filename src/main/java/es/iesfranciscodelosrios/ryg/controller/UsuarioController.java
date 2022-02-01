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

import es.iesfranciscodelosrios.ryg.model.Usuario;
import es.iesfranciscodelosrios.ryg.services.UsuarioService;

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
	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuarios() {
		try {
			List<Usuario> getAllUsuarios = service.getAllUsuario();
			return new ResponseEntity<List<Usuario>>(getAllUsuarios, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			List<Usuario> getAllUsuarios = new ArrayList<Usuario>();
			return new ResponseEntity<List<Usuario>>(getAllUsuarios, new HttpHeaders(), HttpStatus.OK);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver el usuario que le corresponda un id determinado
	 * 
	 * @param id
	 * @return Usuario encontrado en la bd, o vacío si hay algún error
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") Long id) {
		if (id != null && id > -1) {
			try {
				Usuario getUsuarioById = service.getUsuarioById(id);
				return new ResponseEntity<Usuario>(getUsuarioById, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Usuario>(new Usuario(), new HttpHeaders(), HttpStatus.OK);
		}
	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y crear un usuario nuevo
	 * 
	 * @param id
	 * @return Usuario encontrado en la bd, o vacío si hay algún error
	 */
	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
		if (usuario != null && usuario.getId() == -1) {
			try {
				Usuario createUsuario = service.createUsuario(usuario);
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
	 * datos y actualizar un usuario
	 * 
	 * @param id
	 * @return Usuario encontrado en la bd, o vacío si hay algún error
	 */
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
	@DeleteMapping("/{id}")
	public HttpStatus deleteUsuarioById(@PathVariable("id") Long id) {
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
