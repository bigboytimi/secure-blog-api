package com.moonshade.week10secureblogapi.controller;

import com.moonshade.week10secureblogapi.dto.requests.AuthenticationRequest;
import com.moonshade.week10secureblogapi.dto.response.AuthenticationResponse;
import com.moonshade.week10secureblogapi.dto.requests.RegisterRequest;
import com.moonshade.week10secureblogapi.repository.UserRepository;
import com.moonshade.week10secureblogapi.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {
    private UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest authenticationRequest){

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        if(userExists(registerRequest.getUsername())){
            log.info("User exists in db");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Registering user from request");
        AuthenticationResponse response = authenticationService.register(registerRequest);
        return ResponseEntity.ok(response);
    }


    public boolean userExists(String username){
        return userRepository.existsByUsername(username);
    }

}
