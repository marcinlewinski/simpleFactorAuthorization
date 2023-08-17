package com.marcinl.simpleFactorAuthorization.config;

import com.marcinl.simpleFactorAuthorization.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Get the "authorities" claim from the token
        String authoritiesClaim = jwt.getClaim("authorities");

        // Convert the authorities from the claim to GrantedAuthority objects
        if (authoritiesClaim != null && !authoritiesClaim.isEmpty()) {
            String[] authorityArray = authoritiesClaim.split(",");
            for (String authority : authorityArray) {
                authorities.add(new Role(authority.trim())); // Create a Role object implementing GrantedAuthority
            }
        }

        return authorities;
    }
}
