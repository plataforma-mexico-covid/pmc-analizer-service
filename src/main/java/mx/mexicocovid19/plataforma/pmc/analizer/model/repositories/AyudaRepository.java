package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import java.util.List;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AyudaRepository extends JpaRepository<Ayuda, Integer> {
    @Query("select ayuda " +
            " FROM Ayuda ayuda" +
            " where " +
            "   ( 6371 * " +
            "      acos ( " +
            "         cos ( radians ( :latitudeRef ) ) " +
            "         * cos ( radians ( ayuda.ubicacion.latitude ) ) " +
            "         * cos ( radians ( ayuda.ubicacion.longitude ) - radians ( :longitudeRef ) ) " +
            "         + sin ( radians ( :latitudeRef ) ) " +
            "         * sin ( radians ( ayuda.ubicacion.latitude ) ) " +
            "      ) " +
            "   ) " +
            "     <= :kilometers ")
    List<Ayuda> findByAllInsideOfKilometers(@Param("latitudeRef") double latitudeRef,
                                           @Param("longitudeRef") double longitudeRef,
                                           @Param("kilometers") double kilometers);
    @Query("select ayuda " +
            " FROM Ayuda ayuda" +
            " where " +
            "   ayuda.ciudadano != :ciudadano " +
            "   and ayuda.estatusAyuda = 'NUEVA' " +
            "   and ayuda.tipoAyuda = :tipoAyuda " +
            "   and ayuda.origenAyuda = :origenAyuda " +
            "   and ( 6371 * " +
            "      acos ( " +
            "         cos ( radians ( :latitudeRef ) ) " +
            "         * cos ( radians ( ayuda.ubicacion.latitude ) ) " +
            "         * cos ( radians ( ayuda.ubicacion.longitude ) - radians ( :longitudeRef ) ) " +
            "         + sin ( radians ( :latitudeRef ) ) " +
            "         * sin ( radians ( ayuda.ubicacion.latitude ) ) " +
            "      ) " +
            "   ) " +
            "     <= :kilometers ")
    List<Ayuda> findByAllInsideOfKilometersByOrigenAyuda(@Param("latitudeRef") double latitudeRef,
                                                         @Param("longitudeRef") double longitudeRef,
                                                         @Param("kilometers") double kilometers,
                                                         @Param("origenAyuda") OrigenAyuda origenAyuda,
                                                         @Param("tipoAyuda") TipoAyuda tipoAyuda,
                                                         @Param("ciudadano") Ciudadano ciudadano );
    
    @Query("Select ayuda FROM Ayuda ayuda where ayuda.estatusAyuda IN (:status)")
    List<Ayuda> findAllByEstatus(@Param("status") List<EstatusAyuda> statusList);
}
