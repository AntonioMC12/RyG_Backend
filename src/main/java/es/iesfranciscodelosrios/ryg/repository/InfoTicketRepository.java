package es.iesfranciscodelosrios.ryg.repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesfranciscodelosrios.ryg.model.InfoTicket;

@Repository
public interface InfoTicketRepository extends JpaRepository<InfoTicket, Long> {

	@Query(value = "SELECT * FROM info_ticket AS t WHERE t.telefono = ?1", nativeQuery = true)
	public List<InfoTicket> getTicketsByTelephone(int telefono);

	@Query(value = "SELECT * FROM info_ticket AS t WHERE t.fecha_ticket = ?1", nativeQuery = true)
	public List<InfoTicket> getTicketsByDate(LocalDate fecha_ticket);

	@Query(value = "SELECT * FROM info_ticket AS t WHERE t.id_boleto = ?1", nativeQuery = true)
	public Optional<InfoTicket> getTicketByIdBoleto(Long id_boleto);

}
