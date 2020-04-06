package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ciudadano;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.User;

public interface CiudadanoRepository extends JpaRepository<Ciudadano, Integer> {
    Ciudadano findByUser(User user);
}
