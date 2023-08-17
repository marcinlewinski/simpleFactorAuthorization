package com.marcinl.simpleFactorAuthorization.service;

import com.marcinl.simpleFactorAuthorization.model.LoginResponseDTO;
import com.marcinl.simpleFactorAuthorization.model.RegistrationDTO;
import com.marcinl.simpleFactorAuthorization.model.Role;
import com.marcinl.simpleFactorAuthorization.model.User;
import com.marcinl.simpleFactorAuthorization.repository.RoleRepository;
import com.marcinl.simpleFactorAuthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public User registerUser(RegistrationDTO registrationDTO) {
        Optional<Role> getRole = roleRepository.findByAuthority("ROLE_USER");
        Set<Role> authorities = new HashSet<>();
        Role role = getRole.orElseThrow(() -> new NoSuchElementException("Cont not find role!"));

        authorities.add(role);
        return userRepository.save(new User(UUID.randomUUID(), registrationDTO.getUsername(), passwordEncoder.encode(registrationDTO.getPassword()), authorities));
    }

    public LoginResponseDTO loginUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );
            String token = tokenService.generateJwt(authentication);

            System.out.println(token);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        } catch (AuthenticationException exception) {
            exception.printStackTrace();
            return new LoginResponseDTO(null, "");
        }

    }

}
