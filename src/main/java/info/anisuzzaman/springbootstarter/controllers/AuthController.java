package info.anisuzzaman.springbootstarter.controllers;

import info.anisuzzaman.springbootstarter.enums.ERole;
import info.anisuzzaman.springbootstarter.models.Role;
import info.anisuzzaman.springbootstarter.models.User;
import info.anisuzzaman.springbootstarter.models.UserRoleMap;
import info.anisuzzaman.springbootstarter.payload.request.LoginRequest;
import info.anisuzzaman.springbootstarter.payload.request.SignupRequest;
import info.anisuzzaman.springbootstarter.payload.response.JwtResponse;
import info.anisuzzaman.springbootstarter.payload.response.MessageResponse;
import info.anisuzzaman.springbootstarter.repository.RoleRepository;
import info.anisuzzaman.springbootstarter.repository.UserRepository;
import info.anisuzzaman.springbootstarter.repository.UserRoleMapRepository;
import info.anisuzzaman.springbootstarter.security.jwt.JwtUtils;
import info.anisuzzaman.springbootstarter.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : anisuzzaman
 * @created : 1/17/21, Sunday
 **/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleMapRepository userRoleMapRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
        Set<String> strRoles = signUpRequest.getRole();
        List<UserRoleMap> userRoleMaps = new ArrayList<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER);

            userRoleMaps.add(new UserRoleMap(user.getId(), userRole.getId()));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                                if(adminRole==null){
                                   adminRole= roleRepository.save(new Role(ERole.ROLE_ADMIN));
                                }
                        userRoleMaps.add(new UserRoleMap(user.getId(), adminRole.getId()));
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR);
                        if(modRole==null){
                            modRole= roleRepository.save(new Role(ERole.ROLE_ADMIN));
                        }
                        userRoleMaps.add(new UserRoleMap(user.getId(), modRole.getId()));

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                        if(userRole==null){
                            userRole= roleRepository.save(new Role(ERole.ROLE_ADMIN));
                        }
                        userRoleMaps.add(new UserRoleMap(user.getId(), userRole.getId()));

                }
            });
        }



        userRoleMapRepository.saveAll(userRoleMaps);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
