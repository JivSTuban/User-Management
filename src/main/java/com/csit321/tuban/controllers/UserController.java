package com.csit321.tuban.controllers;

import com.csit321.tuban.DTOs.LoginUserDTO;
import com.csit321.tuban.DTOs.RegisterUserDTO;
import com.csit321.tuban.entities.MyUser;
import com.csit321.tuban.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MyUser> getAllUsers() {
        return userServices.getUsers();
    }



    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserDTO input) {
        return userServices.login(input);
    }

    @GetMapping(path = "/search/id",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyUser> getUserById(@RequestParam Integer id) {
        MyUser myUser = userServices.getUserById(id);
        return ResponseEntity.ok(myUser);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestParam Integer id, @RequestBody RegisterUserDTO userDto) {
        return userServices.updateUser(id, userDto);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        return userServices.deleteUser(id);
    }

    @GetMapping(path = "/search/firstname",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MyUser>> searchUsersByFirstName(@RequestParam String firstname) {
        List<MyUser> myUsers = userServices.getUserByFirstName(firstname);
        return ResponseEntity.ok(myUsers);
    }

    @GetMapping(path = "/search/email",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MyUser>> searchUsersByEmail(@RequestParam String email) {
        MyUser myUser = userServices.getUserByEmail(email);
        return ResponseEntity.ok(Collections.singletonList(myUser));
    }

    @GetMapping(path = "/search/lastname",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MyUser>> searchUsersByLastName(@RequestParam String lastname) {
        List<MyUser> myUsers = userServices.getUserByLastName(lastname);
        return ResponseEntity.ok(myUsers);
    }

    @GetMapping(path = "/deleted",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MyUser> getAllDeletedUsers() {
        return userServices.getAllDeletedUsers();
    }
}
