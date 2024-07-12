package com.csit321.tuban.controllers;

import com.csit321.tuban.dtos.LoginUserDto;
import com.csit321.tuban.dtos.RegisterUserDto;
import com.csit321.tuban.entities.User;
import com.csit321.tuban.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping
    public List<User> getAllUsers(){
        return userServices.getUsers();
    }

    @PostMapping("/register")
    private ResponseEntity<?> signup(@RequestBody RegisterUserDto input){
        return userServices.signup(input);
    }

    @GetMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginUserDto input){
        return userServices.login(input);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam("id") Integer userId, @RequestBody RegisterUserDto userDto) {
        return userServices.updateUser(userId, userDto);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<?> login(@RequestParam Integer id){
        return userServices.deleteUser(id);
    }



}
