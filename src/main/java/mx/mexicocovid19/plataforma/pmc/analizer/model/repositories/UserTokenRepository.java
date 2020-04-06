package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import org.springframework.data.repository.CrudRepository;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.UserToken;

public interface UserTokenRepository extends CrudRepository<UserToken, String> {
}
