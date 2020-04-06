package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Province;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {
}
