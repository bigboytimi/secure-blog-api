package com.moonshade.week10secureblogapi.exceptions;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(Long id){
        super("NOT AUTHORIZED");
    }
}
