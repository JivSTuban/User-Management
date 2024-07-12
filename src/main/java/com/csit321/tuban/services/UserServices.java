package com.csit321.tuban.services;

import com.csit321.tuban.dtos.RegisterUserDto;
import com.csit321.tuban.dtos.LoginUserDto;
import com.csit321.tuban.entities.Role;
import com.csit321.tuban.entities.RoleEnum;
import com.csit321.tuban.entities.User;
import com.csit321.tuban.repositories.RoleRepository;
import com.csit321.tuban.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<?> signup(RegisterUserDto input) {
        if (userRepository.findByEmail(input.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"Email is already in use\"}");
        }

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);
        if (optionalRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Role not found\"}");
        }

        var user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(input.getPassword());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setDeleted(false);
        user.setRole(optionalRole.get());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /*
        public boolean login(LoginUserDto userDto) {
            User user = userRepository.findByEmail(userDto.getEmail());
            if (user == null) {
                return false;
            }
            return passwordEncoder.matches(userDto.getPassword(), user.getPassword());
        }
    */

    public ResponseEntity<?> login(LoginUserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Incorrect Email.\"}");
        }
        if (!Objects.equals(userDto.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Incorrect Password\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Login Successful\"}");
    }

    public ResponseEntity<?> updateUser(Integer userId, RegisterUserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");
        }

        User user = optionalUser.get();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    /*
        public ResponseEntity<?> deleteUser(Integer userId) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");
            }

            User user = optionalUser.get();
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User deleted successfully\"}");
        }
    */
    public ResponseEntity<?> deleteUser(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");
        }

        User user = optionalUser.get();
        user.setDeleted(true);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User safely deleted.\"}");
    }
}

