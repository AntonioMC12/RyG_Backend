package es.iesfranciscodelosrios.ryg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesfranciscodelosrios.ryg.model.Premio;

@Repository

public interface PremioRepository extends JpaRepository<Premio,Integer> {
	 @Query(
	    		value="SELECT * FROM Premio as p WHERE p.Descripcion LIKE %?1% ",
	    		nativeQuery = true)
	    		
	    		

	   
	    public List<Premio> getByDescripcion(String descripcion);
	
	 
	

}
