package mx.mexicocovid19.plataforma.pmc.analizer.model.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.User;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.UserRole;

/**
 * Created by betuzo on 27/01/15.
 */
public interface UserRoleRepository extends CrudRepository<UserRole, String> {
    Set<UserRole> findAllByUser(User user);
}