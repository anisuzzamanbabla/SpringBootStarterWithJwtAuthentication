package info.anisuzzaman.springbootstarter.repository;

import info.anisuzzaman.springbootstarter.enums.ERole;
import info.anisuzzaman.springbootstarter.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByName(ERole name);
}
