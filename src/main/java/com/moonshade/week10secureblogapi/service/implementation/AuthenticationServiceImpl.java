package com.moonshade.week10secureblogapi.service.implementation;

import com.moonshade.week10secureblogapi.dto.requests.AuthenticationRequest;
import com.moonshade.week10secureblogapi.dto.response.AuthenticationResponse;
import com.moonshade.week10secureblogapi.dto.requests.RegisterRequest;
import com.moonshade.week10secureblogapi.entity.Role;
import com.moonshade.week10secureblogapi.entity.UserEntity;
import com.moonshade.week10secureblogapi.exceptions.UserNotFoundException;
import com.moonshade.week10secureblogapi.exceptions.UsernameNotFoundException;
import com.moonshade.week10secureblogapi.repository.UserRepository;
import com.moonshade.week10secureblogapi.security.JwtTokenService;
import com.moonshade.week10secureblogapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        if(request.getRole().equals("ADMIN")){
            user.setRole(Role.ADMIN);
        } else{
            user.setRole(Role.USER);
        }
        userRepository.save(user);
        log.info("User saved in DB");
        String jwtToken = jwtTokenService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        log.info("Authenticating user");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));
        log.info("Finding user from repository");
        UserDetails user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(authenticationRequest.getUsername()));
        log.info("Generating token from user");
        String jwtToken = jwtTokenService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
