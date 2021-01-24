package info.anisuzzaman.springbootstarter.security.services;

import info.anisuzzaman.springbootstarter.models.Role;
import info.anisuzzaman.springbootstarter.models.User;
import info.anisuzzaman.springbootstarter.models.UserRoleMap;
import info.anisuzzaman.springbootstarter.repository.RoleRepository;
import info.anisuzzaman.springbootstarter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		Set<Long> ids=new HashSet<>();
		for (UserRoleMap userRoleMap :
				user.getUserRoleMap()) {
			ids.add(userRoleMap.getRoleId());
		}

		List<Role> userRoles = (List<Role>) roleRepository.findAllById(ids);
		return UserDetailsImpl.build(user,userRoles);
	}

}
