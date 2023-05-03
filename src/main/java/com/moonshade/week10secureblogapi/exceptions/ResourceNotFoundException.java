package com.moonshade.week10secureblogapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s) {
        super("User with username " + s + " does not exist");
    }
}
