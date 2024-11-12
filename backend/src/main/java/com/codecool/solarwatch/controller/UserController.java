package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.payload.NewAppUserDTO;
import com.codecool.solarwatch.model.payload.AppUserRequestDTO;
import com.codecool.solarwatch.service.UserService;
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
    public ResponseEntity<Void> createUser(@RequestBody NewAppUserDTO signUpRequest) {
        return userService.createUser(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AppUserRequestDTO loginRequest) {
        return userService.authenticateUser(loginRequest);
    }
}
