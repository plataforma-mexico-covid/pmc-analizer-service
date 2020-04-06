package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Peticion;

public interface PeticionRepository extends JpaRepository<Peticion, Integer> {
}
