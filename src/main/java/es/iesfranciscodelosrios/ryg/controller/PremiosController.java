package es.iesfranciscodelosrios.ryg.controller;

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

import es.iesfranciscodelosrios.ryg.model.Premios;
import es.iesfranciscodelosrios.ryg.services.PremiosService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/premio")

public class PremiosController {
	@Autowired
    PremiosService service;
	
	
	@GetMapping
    public ResponseEntity<List<Premios>> getAllPremios() {
        List<Premios> list = service.getAllPremios();
 
        return new ResponseEntity<List<Premios>>(list, new HttpHeaders(), HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Premios> getPremiosById(@PathVariable("id") Integer id)
                                                    throws Exception {
    	Premios entity = service.getPremiosById(id);
 
        return new ResponseEntity<Premios>(entity, new HttpHeaders(), HttpStatus.OK);
    }
	
	 @GetMapping("/description/{description}")
	    public ResponseEntity<List<Premios>> getPremiosByDescription(@PathVariable("description") String description)
	       {
	    	List <Premios> list = service.getPremiosByDescription(description);
	 
	        return new ResponseEntity <List<Premios>>(list, new HttpHeaders(), HttpStatus.OK);
	    }
	 
	 @PostMapping
	    public ResponseEntity<Premios> createPremios(@Valid @RequestBody Premios myPremio){
	    	Premios created = service.createPremio(myPremio);
	        return new ResponseEntity<Premios>(created, new HttpHeaders(), HttpStatus.OK);
	    }
	 
	 @PutMapping
	    public ResponseEntity<Premios> updatePremio(@Valid @RequestBody Premios myPremio)
	                                                    throws Exception {
	    	Premios updated = service.UpdatePremio(myPremio);
	        return new ResponseEntity<Premios>(updated, new HttpHeaders(), HttpStatus.OK);
	    }
	 
	 @DeleteMapping("/{id}")
	    public HttpStatus deletePremioById(@PathVariable("id") Integer id)
	                                                    throws Exception {
	        service.deletePremioById(id);
	        return HttpStatus.ACCEPTED;
	    }

}