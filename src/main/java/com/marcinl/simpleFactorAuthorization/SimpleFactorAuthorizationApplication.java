package com.marcinl.simpleFactorAuthorization;

import com.marcinl.simpleFactorAuthorization.model.Role;
import com.marcinl.simpleFactorAuthorization.model.User;
import com.marcinl.simpleFactorAuthorization.repository.RoleRepository;
import com.marcinl.simpleFactorAuthorization.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class SimpleFactorAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleFactorAuthorizationApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository repository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.findByAuthority("ROLE_ADMIN").isPresent()) return;
            try {
                Role adminRole = repository.save(new Role("ROLE_ADMIN"));
                repository.save(new Role("ROLE_USER"));
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                User admin = new User(UUID.randomUUID(), "admin", passwordEncoder.encode("password"), roles);
                System.out.println(admin);
                userRepository.save(admin);
            } catch (Error e) {
                e.printStackTrace();
            }

        };
    }

}
