package com.moonshade.week10secureblogapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorHandler> handleAuthorNotFound(UserNotFoundException e){
        ErrorHandler response = new ErrorHandler(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorHandler> handlerUsernameNotFound(UsernameNotFoundException ex){
        ErrorHandler response = new ErrorHandler(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHandler> handleGeneralException(Exception ex){
        if(ex instanceof NullPointerException){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ErrorHandler response = new ErrorHandler(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorHandler> handleCategoryNotFound(CategoryNotFoundException e){
        ErrorHandler response = new ErrorHandler(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorHandler> handleCommentNotFound(CommentNotFoundException e){
        ErrorHandler response = new ErrorHandler(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorHandler> handleNoDataFound(NoDataFoundException e){
        ErrorHandler response = new ErrorHandler(e.getMessage(),LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorHandler> handlePostNotFound(PostNotFoundException e){
        ErrorHandler response = new ErrorHandler(e.getMessage(),LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorHandler> handleAccessDenied(AccessDeniedException e){
        ErrorHandler response = new ErrorHandler(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<ErrorHandler> handlePostNotFound(LikeNotFoundException e){
        ErrorHandler response = new ErrorHandler(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
