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
	@GetMapping
	public ResponseEntity<List<Premio>> getAllPremios() {
		try {
			List<Premio> list = service.getAllPremios();
			return new ResponseEntity<List<Premio>>(list, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			List<Premio> list = new ArrayList<Premio>();
			return new ResponseEntity<List<Premio>>(list, new HttpHeaders(), HttpStatus.OK);
		}

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y devolver el premio que le corresponda un id determinado
	 * 
	 * @param id
	 * @return premio encontrado en la bd, o vacío si hay algún error
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Premio> getPremiosById(@PathVariable("id") Integer id) {
		if (id != null && id > -1) {
			try {
				Premio entity = service.getPremiosById(id);
				return new ResponseEntity<Premio>(entity, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<Premio>(new Premio(), new HttpHeaders(), HttpStatus.OK);

			}
		} else {
			return new ResponseEntity<Premio>(new Premio(), new HttpHeaders(), HttpStatus.OK);
		}

	}

	@GetMapping("/description/{description}")
	public ResponseEntity<List<Premio>> getPremiosByDescription(@PathVariable("description") String description) {
		List<Premio> list = service.getPremiosByDescription(description);

		return new ResponseEntity<List<Premio>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Método que recoge una petición http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Premios entregados.
	 * 
	 * @return Lista con todos los premios entregados de la base de datos, una lista
	 *         vacía si algo ha ido mal
	 */
	@GetMapping("/entregado")
	public ResponseEntity<List<Premio>> getPremiosEntregados() {
		
			try {
				List<Premio> getPremiosEntregados = service.getPremiosEntregados();
				return new ResponseEntity<List<Premio>>(getPremiosEntregados, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				List<Premio> getPremiosEntregados = new ArrayList<Premio>();
				return new ResponseEntity<List<Premio>>(getPremiosEntregados, new HttpHeaders(), HttpStatus.OK);
			}
		

	}
	
	/**
	 * Método que recoge una petición http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Premios no entregados.
	 * 
	 * @return Lista con todos los premios no entregados de la base de datos, una lista
	 *         vacía si algo ha ido mal
	 */
	@GetMapping("/no-entregado")
	public ResponseEntity<List<Premio>> getPremiosNoEntregados() {
		
			try {
				List<Premio> getPremiosNoEntregados = service.getPremiosNoEntregados();
				return new ResponseEntity<List<Premio>>(getPremiosNoEntregados, new HttpHeaders(), HttpStatus.OK);
			} catch (Exception e) {
				List<Premio> getPremiosNoEntregados = new ArrayList<Premio>();
				return new ResponseEntity<List<Premio>>(getPremiosNoEntregados, new HttpHeaders(), HttpStatus.OK);
			}
	

	}

	/**
	 * Método que recoge una petición http para hacer una consulta a la base de
	 * datos y crear un premio nuevo
	 * 
	 * @param id
	 * @return premio encontrado en la bd, o vacío si hay algún error
	 */
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
	@DeleteMapping("/{id}")
	public HttpStatus deletePremioById(@PathVariable("id") Integer id) {
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