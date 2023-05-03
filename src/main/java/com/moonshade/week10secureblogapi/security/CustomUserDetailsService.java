package com.moonshade.week10secureblogapi.security;

import com.moonshade.week10secureblogapi.entity.Role;
import com.moonshade.week10secureblogapi.entity.UserEntity;
import com.moonshade.week10secureblogapi.exceptions.UserNotFoundException;
import com.moonshade.week10secureblogapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


//this class is simply for overriding UserDetails interface
//so as to have access to its loadbyusername() method which is
//used to fetch user details from database.
// this method is used by AuthenticationManager to get UserDetails when
//authenticating/verifying against the one provided by anyone trying to log in
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
        return new CustomUserDetails(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().toString())));
    }



}
