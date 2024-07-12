package com.csit321.tuban.controllers;

import com.csit321.tuban.models.LoginUserModel;
import com.csit321.tuban.models.RegisterUserModel;
import com.csit321.tuban.entities.User;
import com.csit321.tuban.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public List<User> getAllUsers() {
        return userServices.getUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserModel input) {
        return userServices.signup(input);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserModel input) {
        return userServices.login(input);
    }

    @GetMapping("/search/id")
    public ResponseEntity<User> getUserById(@RequestParam Integer id) {
        User user = userServices.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestParam Integer id, @RequestBody RegisterUserModel userDto) {
        return userServices.updateUser(id, userDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        return userServices.deleteUser(id);
    }

    @GetMapping("/search/firstname")
    public ResponseEntity<List<User>> searchUsersByFirstName(@RequestParam String firstname) {
        List<User> users = userServices.getUserByFirstName(firstname);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<User>> searchUsersByEmail(@RequestParam String email) {
        User user = userServices.getUserByEmail(email);
        return ResponseEntity.ok(Collections.singletonList(user));
    }

    @GetMapping("/search/lastname")
    public ResponseEntity<List<User>> searchUsersByLastName(@RequestParam String lastname) {
        List<User> users = userServices.getUserByLastName(lastname);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/deleted")
    public List<User> getAllDeletedUsers() {
        return userServices.getAllDeletedUsers();
    }
}
