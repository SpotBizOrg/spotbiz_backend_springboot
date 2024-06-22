package com.spotbiz.spotbiz_backend_springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        String message = ex.getMessage();
        if ("User not found".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if ("Invalid credentials".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        if ("Customer already exists".equals(message) || "Username already exists".equals(message) || "Email already exists".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else if ("User not found".equals(message)) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
