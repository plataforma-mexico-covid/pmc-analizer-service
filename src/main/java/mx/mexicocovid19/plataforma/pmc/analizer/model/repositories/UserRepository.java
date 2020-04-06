package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import org.springframework.data.repository.CrudRepository;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.User;

/**
 * Created by betuzo on 25/01/15.
 */
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
