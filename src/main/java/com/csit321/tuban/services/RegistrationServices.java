package com.csit321.tuban.services;

import com.csit321.tuban.DTOs.RegisterUserDTO;
import com.csit321.tuban.entities.MyUser;
import com.csit321.tuban.entities.Role;
import com.csit321.tuban.entities.RoleEnum;
import com.csit321.tuban.repositories.RoleRepository;
import com.csit321.tuban.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServices {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServices(@Lazy UserRepository userRepository, @Lazy RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> signup(RegisterUserDTO input) {
        try{
            if (userRepository.count() > 0) {
                if (userRepository.findByEmailAndIsDeletedTrue(input.getEmail()) != null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"This account was already deleted. Would you like to recover this account?\"}");
                }
            }
        }catch(NullPointerException e){
            System.out.println("There are no deleted accounts yet.");
        }

        if (userRepository.findByEmailAndIsDeletedFalse(input.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"Email is already in use\"}");
        }

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Role not found\"}");
        }

        var user = new MyUser();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setPhoneNumber(input.getPhoneNumber());
        user.setIsDeleted(false);
        user.setRole(optionalRole.get());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
