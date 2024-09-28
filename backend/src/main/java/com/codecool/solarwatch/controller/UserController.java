package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.payload.CreateUserRequest;
import com.codecool.solarwatch.model.payload.UserRequest;
import com.codecool.solarwatch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest signUpRequest) {
        return userService.createUser(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }
}
