package info.anisuzzaman.springbootstarter.repository;

import info.anisuzzaman.springbootstarter.models.UserRoleMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

@Repository
public interface UserRoleMapRepository extends CrudRepository<UserRoleMap,Long> {

}
