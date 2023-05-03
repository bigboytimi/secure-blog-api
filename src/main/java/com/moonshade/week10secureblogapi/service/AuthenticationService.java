package com.moonshade.week10secureblogapi.service;

import com.moonshade.week10secureblogapi.dto.requests.AuthenticationRequest;
import com.moonshade.week10secureblogapi.dto.response.AuthenticationResponse;
import com.moonshade.week10secureblogapi.dto.requests.RegisterRequest;

public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
