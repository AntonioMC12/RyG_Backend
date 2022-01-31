package es.iesfranciscodelosrios.ryg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesfranciscodelosrios.ryg.model.Premios;

@Repository

public interface PremiosRepository extends JpaRepository<Premios,Integer> {
	 @Query(
	    		value="SELECT * FROM Premios as p WHERE p.Descripcion LIKE %?1% ",
	    		nativeQuery = true)
	    		
	    		

	   
	    public List<Premios> getByDescripcion(String descripcion);
	
	 
	

}
