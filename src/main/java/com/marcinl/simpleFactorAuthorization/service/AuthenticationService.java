package com.marcinl.simpleFactorAuthorization.service;

import com.marcinl.simpleFactorAuthorization.model.RegistrationDTO;
import com.marcinl.simpleFactorAuthorization.model.Role;
import com.marcinl.simpleFactorAuthorization.model.User;
import com.marcinl.simpleFactorAuthorization.repository.RoleRepository;
import com.marcinl.simpleFactorAuthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegistrationDTO registrationDTO) {
        Optional<Role> getRole = roleRepository.findByAuthority("ROLE_USER");
        Set<Role> authorities = new HashSet<>();
        Role role = getRole.orElseThrow(() -> new NoSuchElementException("Cont not find role!"));

        authorities.add(role);
        return userRepository.save(new User(UUID.randomUUID(), registrationDTO.getUsername(), passwordEncoder.encode(registrationDTO.getPassword()), authorities));
    }
}
