package com.fighter.byteblog.controllers;


import com.fighter.byteblog.exceptions.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error("exception caught " , e);
        ApiErrorResponse error = ApiErrorResponse.builder().
                status(HttpStatus.INTERNAL_SERVER_ERROR.value()).
                message("an exception caught " + e.getMessage()).build();
        return new ResponseEntity<>(error , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(Exception e) {
        ApiErrorResponse error = ApiErrorResponse.builder().
                status(HttpStatus.BAD_REQUEST.value()).
                message(e.getMessage()).build();
        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity handleIllegalStateException(Exception e) {
        ApiErrorResponse error = ApiErrorResponse.builder().
                status(HttpStatus.CONFLICT.value()).
                message(e.getMessage()).build();
        return new ResponseEntity<>(error , HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentialsException(Exception e , WebRequest web) {
        ApiErrorResponse error = ApiErrorResponse.builder().
                status(HttpStatus.UNAUTHORIZED.value()).
                message("InCorrect credentials " + e.getMessage() ).build();
        log.error("InCorrect credentials " + e.getMessage()  + web.getContextPath());
        return new ResponseEntity<>(error , HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleUsernameNotFoundException(Exception e , WebRequest web) {
        ApiErrorResponse error = ApiErrorResponse.builder().
                status(HttpStatus.UNAUTHORIZED.value()).
                message("user not found fuck you " + e.getMessage() ).build();
        return new ResponseEntity<>(error , HttpStatus.UNAUTHORIZED);
    }



}
