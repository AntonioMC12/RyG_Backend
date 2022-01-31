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

@RestController
@RequestMapping("/boleto")
public class BoletoController {

	@Autowired
	BoletoService service;

	/**
	 * Método que recoge una petición http a nuestra API para hacer una consulta a
	 * la base de datos y devolver todos los Boletos.
	 * 
	 * @return Lista con todos los boletos de la base de datos, una lista vacía si
	 *         algo ha ido mal
	 */
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
	 * Método que recoge una petición http a nuestra API para hacer una consulta a
	 * la base de datos y obtener el boleto con el id que pasamos por parámetro
	 * 
	 * @param id del boleto a buscar
	 * @return Boleto encontrado, o boleto vacío si hay algún error.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Boleto> getBoletoById(@PathVariable("id") Long id) {
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
	 * Método que recoge una petición http a nuestra API para hacer una consulta a
	 * la base de datos y añadir un boleto.
	 * 
	 * @param boleto a añadir
	 * @return boleto con el id añadido o boleto vacío si ha ido mal.
	 */
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
	 * Método que recoge una petición http a nuestra API para hacer una consulta a
	 * la base de datos y actualilzar un boleto.
	 * 
	 * @param boleto a añadir
	 * @return boleto actualizado o boleto vacío si ha ido mal.
	 */
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
	 * Método para borrar un boleto de la base de datos por su id
	 * 
	 * @param id del boleto a borrar
	 * @return Ok si lo borra, Bad_Request.
	 */
	@DeleteMapping("/{id}")
	public HttpStatus deleteBoletoById(@PathVariable("id") Long id) {
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
