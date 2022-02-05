package es.iesfranciscodelosrios.ryg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesfranciscodelosrios.ryg.model.InfoTicket;

@Repository
public interface InfoTicketRepository extends JpaRepository<InfoTicket, Long> {

	@Query(value = "SELECT * FROM info_ticket AS t WHERE t.telefono = ?1", nativeQuery = true)
	public List<InfoTicket> getTicketsByTelephone(int telefono);

}
