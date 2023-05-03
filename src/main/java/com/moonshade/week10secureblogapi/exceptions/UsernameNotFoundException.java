package com.moonshade.week10secureblogapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String username){
        super("USER WITH Username " + username + " NOT FOUND");
    }
}
