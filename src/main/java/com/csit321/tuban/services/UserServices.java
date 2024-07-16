package com.csit321.tuban.services;

import com.csit321.tuban.DTOs.RegisterUserDTO;
import com.csit321.tuban.DTOs.LoginUserDTO;
import com.csit321.tuban.entities.MyUser;
import com.csit321.tuban.entities.Role;
import com.csit321.tuban.entities.RoleEnum;
import com.csit321.tuban.repositories.RoleRepository;
import com.csit321.tuban.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServices implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServices(@Lazy UserRepository userRepository, @Lazy RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<MyUser> getUsers(){
        return userRepository.findAll();
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
        MyUser myUser = userRepository.findByEmailAndIsDeletedTrue(userDto.getEmail());

        if (myUser != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"This account is in a state of deletion. Would you like to recover it?\"}");
        }

        myUser = userRepository.findByEmailAndIsDeletedFalse(userDto.getEmail());
        if (myUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Incorrect Email\"}");
        }
        if (!passwordEncoder.matches(userDto.getPassword(), myUser.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Incorrect Password\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Login Successful\"}");
    }

    public ResponseEntity<?> updateUser(Integer userId, RegisterUserDTO userDto) {
        Optional<MyUser> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");
        }

        if (optionalUser.get().getIsDeleted()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\": \"Cannot Update profile because this user was already deleted\"}");
        }

        MyUser myUser = optionalUser.get();
        myUser.setFirstName(userDto.getFirstName());
        myUser.setLastName(userDto.getLastName());
        myUser.setEmail(userDto.getEmail());
        myUser.setPassword(userDto.getPassword());
        myUser.setPhoneNumber(userDto.getPhoneNumber());

        userRepository.save(myUser);
        return ResponseEntity.status(HttpStatus.OK).body(myUser);
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
        Optional<MyUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User not found\"}");
        }

        MyUser myUser = optionalUser.get();
        myUser.setIsDeleted(true);
        userRepository.save(myUser);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User deleted\"}");
    }

    public MyUser getUserById(Integer id){
        return userRepository.findByIdAndIsDeletedFalse(id);
    }

    public MyUser getUserByEmail(String email){
        return userRepository.findByEmailAndIsDeletedFalse(email);
    }

    public List<MyUser> getUserByFirstName(String firstName){
        return userRepository.findAllByFirstNameAndIsDeletedFalse(firstName);
    }

    public List<MyUser> getUserByLastName(String lastName){
        return userRepository.findAllByLastNameAndIsDeletedFalse(lastName);
    }

    public List<MyUser> getAllDeletedUsers(){
        return userRepository.findAllByIsDeletedTrue();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findByEmailAndIsDeletedFalse(email);
        if (myUser != null){
            return User.builder()
                    .username(myUser.getEmail())
                    .password(myUser.getPassword())
                    .roles(String.valueOf(myUser.getRole().getName()))
                    .build();

        }else{
            throw new UsernameNotFoundException(email);
        }
    }
}

