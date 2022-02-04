package es.iesfranciscodelosrios.ryg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesfranciscodelosrios.ryg.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "SELECT * FROM usuario AS u WHERE u.latitud = ?1 AND u.longitud = ?2 LIMIT 1", nativeQuery = true)
	public Optional<Usuario> getUsuarioByCoordinates(float latitud, float longitud);

}
