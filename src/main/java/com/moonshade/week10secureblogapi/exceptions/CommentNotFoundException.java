package com.moonshade.week10secureblogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Long id){
        super("COMMENT WITH ID " +id + " NOT FOUND");
    }
}
