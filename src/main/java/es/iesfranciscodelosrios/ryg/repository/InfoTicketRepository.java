package es.iesfranciscodelosrios.ryg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesfranciscodelosrios.ryg.model.InfoTicket;

@Repository
public interface InfoTicketRepository extends JpaRepository<InfoTicket, Long>{

}
