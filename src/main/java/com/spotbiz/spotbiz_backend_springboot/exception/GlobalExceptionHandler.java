package com.spotbiz.spotbiz_backend_springboot.exception;

import com.spotbiz.spotbiz_backend_springboot.entity.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        String message = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(message);

        if ("User not found".equals(message)) {
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } else if ("Invalid credentials".equals(message)) {
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(new ErrorResponse("An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(message);

        if ("Customer already exists".equals(message) || "Username already exists".equals(message) || "Email already exists".equals(message)) {
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } else if ("User not found".equals(message)) {
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new ErrorResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(DuplicateReviewException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateReviewException(DuplicateReviewException ex) {
        String message = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdvertisementException.JsonConversionException.class)
    public ResponseEntity<ErrorResponse> handleJsonConversionException(AdvertisementException.JsonConversionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdvertisementException.BusinessNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBusinessNotFoundException(AdvertisementException.BusinessNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdvertisementException.AdvertisementSaveException.class)
    public ResponseEntity<ErrorResponse> handleAdvertisementSaveException(AdvertisementException.AdvertisementSaveException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
