package com.csit321.tuban.controllers;

import com.csit321.tuban.DTOs.RegisterUserDTO;
import com.csit321.tuban.services.RegistrationServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegistrationServices registrationServices;

    @PostMapping(path = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDTO input) {
        return registrationServices.signup(input);
    }
}
