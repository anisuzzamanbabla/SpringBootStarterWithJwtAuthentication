package info.anisuzzaman.springbootstarter.security.services;

import info.anisuzzaman.springbootstarter.models.Role;
import info.anisuzzaman.springbootstarter.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import info.anisuzzaman.springbootstarter.models.UserRoleMap;
import info.anisuzzaman.springbootstarter.repository.RoleRepository;
import info.anisuzzaman.springbootstarter.repository.UserRoleMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;


    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user,List<Role> userRoles) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (Role role :
                userRoles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
