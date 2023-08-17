package com.marcinl.simpleFactorAuthorization.controller;

import com.marcinl.simpleFactorAuthorization.model.LoginResponseDTO;
import com.marcinl.simpleFactorAuthorization.model.RegistrationDTO;
import com.marcinl.simpleFactorAuthorization.model.User;
import com.marcinl.simpleFactorAuthorization.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return authenticationService.registerUser(registrationDTO);
    }
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO registrationDTO) {

        return authenticationService.loginUser(registrationDTO.getUsername(),registrationDTO.getPassword());
    }

}

