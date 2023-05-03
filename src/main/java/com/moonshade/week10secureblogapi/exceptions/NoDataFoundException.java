package com.moonshade.week10secureblogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoDataFoundException extends RuntimeException{
    public NoDataFoundException(Long id){
        super("DATA NOT FOUND. ID is " + id);
    }

}
