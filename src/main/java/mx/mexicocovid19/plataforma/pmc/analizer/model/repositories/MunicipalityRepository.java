package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Municipality;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Province;

public interface MunicipalityRepository extends JpaRepository<Municipality, Integer> {
    List<Municipality> findAllByProvince(final Province province);
}
