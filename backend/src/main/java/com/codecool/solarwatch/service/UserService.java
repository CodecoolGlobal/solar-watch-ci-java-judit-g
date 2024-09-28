package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.CreateUserRequest;
import com.codecool.solarwatch.model.payload.JwtResponse;
import com.codecool.solarwatch.model.payload.UserRequest;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    //in case user couldn't be created, should it throw an exception? should it return a different HttpStatus? What's the correct way of handling this issue?
    //Todo: handle it either way!
    public ResponseEntity<Void> createUser(CreateUserRequest signUpRequest) {
        UserEntity userEntity = new UserEntity(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), Set.of(Role.ROLE_USER));
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> authenticateUser(UserRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }
}
