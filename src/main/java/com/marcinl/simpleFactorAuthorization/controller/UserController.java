package com.marcinl.simpleFactorAuthorization.controller;

import com.marcinl.simpleFactorAuthorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/add")
//    public ResponseEntity<String> addUser(@RequestBody User user) {
//        try {
//            User savedUser = userService.addUser(user);
//            return ResponseEntity.ok("User created with Id:" + savedUser.getUsername());
//        } catch (Error error) {
//            return ResponseEntity.badRequest().body("User cant be created because: " + error.getMessage());
//        }
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok().body(userService.getAllUsers());
//    }

    @GetMapping("/hello")
    public String hello() {
        return "This is user's content!";
    }

}
