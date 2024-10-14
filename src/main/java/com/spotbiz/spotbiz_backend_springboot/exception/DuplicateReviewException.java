package com.spotbiz.spotbiz_backend_springboot.exception;

public class DuplicateReviewException extends RuntimeException{
    public DuplicateReviewException(String message) {
        super(message);
    }
}
