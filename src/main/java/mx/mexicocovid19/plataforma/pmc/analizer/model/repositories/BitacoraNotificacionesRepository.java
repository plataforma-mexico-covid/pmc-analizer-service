package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.BitacoraNotificaciones;

@Repository
public interface BitacoraNotificacionesRepository extends JpaRepository<BitacoraNotificaciones, Integer> {
	@Query("Select bitacora from BitacoraNotificaciones bitacora where bitacora.ayuda = :ayuda")
	public BitacoraNotificaciones findByAyuda(@Param("ayuda") Ayuda ayuda);
}
