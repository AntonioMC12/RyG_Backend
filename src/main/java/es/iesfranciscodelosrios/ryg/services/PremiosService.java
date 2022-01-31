package es.iesfranciscodelosrios.ryg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesfranciscodelosrios.ryg.exceptions.RecordNotFoundException;
import es.iesfranciscodelosrios.ryg.model.Premios;
import es.iesfranciscodelosrios.ryg.repository.PremiosRepository;

@Service
public class PremiosService {
	@Autowired
	PremiosRepository premiosrepository;
	
	public List<Premios> getAllPremios()
    {
        List<Premios> premiosList = premiosrepository.findAll();
         
        if(premiosList.size() > 0) {
            return premiosList;
        } else {
            return new ArrayList<Premios>();
        }
    }
	
	public Premios getPremiosById(Integer id) throws Exception
    {
        Optional<Premios> premio = premiosrepository.findById(id);
        
        if(premio.isPresent()) {
        	
            return premio.get();
        } else {
            throw new RecordNotFoundException("No premio record exist for given id",id);
        }
    }
	
	public Premios createPremio(Premios entity){
        entity = premiosrepository.save(entity);
        return entity;
    }
	
	public Premios UpdatePremio(Premios entity) throws Exception
    {
    	    	
    	if(entity.getId()!=0)
    	{
    	  Optional<Premios> premio = premiosrepository.findById(entity.getId());
        
            if(premio.isPresent())
            {
                Premios newEntity = premio.get();
                
                newEntity.setDescription(entity.getDescription());
                newEntity.setEntregado(entity.isEntregado());

                newEntity = premiosrepository.save(newEntity);

                return newEntity;
            } else {
                throw new RecordNotFoundException("Premio not found",entity.getId());
            }
        }else{
    		throw new RecordNotFoundException("No id of premio given",0);
    	}	    
 }
	
	public void deletePremioById(Integer id) throws Exception
    {
        Optional<Premios> premio = premiosrepository.findById(id);
         
        if(premio.isPresent())
        {
        	
        		premiosrepository.deleteById(id);
        	
        } else {
            throw new RecordNotFoundException("No premio record exist for given id",id);
        }
    }
	
	public List<Premios> getPremiosByDescription(String description) {
    	List<Premios> premiosList = premiosrepository.getByDescripcion(description);
        
        if(premiosList.size() > 0) {
            return premiosList;
        } else {
            return new ArrayList<Premios>();
        }
        
    }

}
