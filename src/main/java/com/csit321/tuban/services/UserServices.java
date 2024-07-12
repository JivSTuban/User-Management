package com.csit321.tuban.services;

import com.csit321.tuban.DTOs.RegisterUserDTO;
import com.csit321.tuban.DTOs.LoginUserDTO;
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

        var user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(input.getPassword());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setIsDeleted(false);
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

    public ResponseEntity<?> login(LoginUserDTO userDto) {
        User user = userRepository.findByEmailAndIsDeletedTrue(userDto.getEmail());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"This account is in a state of deletion. Would you like to recover it?\"}");
        }

        user = userRepository.findByEmailAndIsDeletedFalse(userDto.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Incorrect Email\"}");
        }
        if (!Objects.equals(userDto.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Incorrect Password\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Login Successful\"}");
    }

    public ResponseEntity<?> updateUser(Integer userId, RegisterUserDTO userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");
        }

        if (optionalUser.get().getIsDeleted()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\": \"Cannot Update profile because this user was already deleted\"}");
        }

        User user = optionalUser.get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
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
        user.setIsDeleted(true);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User deleted\"}");
    }

    public User getUserById(Integer id){
        return userRepository.findByIdAndIsDeletedFalse(id);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmailAndIsDeletedFalse(email);
    }

    public List<User> getUserByFirstName(String firstName){
        return userRepository.findAllByFirstNameAndIsDeletedFalse(firstName);
    }

    public List<User> getUserByLastName(String lastName){
        return userRepository.findAllByLastNameAndIsDeletedFalse(lastName);
    }

    public List<User> getAllDeletedUsers(){
        return userRepository.findAllByIsDeletedTrue();
    }

}

