package info.anisuzzaman.springbootstarter.repository;

import info.anisuzzaman.springbootstarter.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
