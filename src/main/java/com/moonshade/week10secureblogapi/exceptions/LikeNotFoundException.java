package com.moonshade.week10secureblogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LikeNotFoundException extends RuntimeException{
    public LikeNotFoundException(Long id){
        super("LIKE WITH ID " + id + " NOT FOUND");
    }


}
