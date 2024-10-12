package com.spotbiz.spotbiz_backend_springboot.exception;

public class AdvertisementException {

    public static class JsonConversionException extends RuntimeException {
        public JsonConversionException(String message) {
            super(message);
        }

        public JsonConversionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class BusinessNotFoundException extends RuntimeException {
        public BusinessNotFoundException(String message) {
            super(message);
        }
    }

    public static class AdvertisementSaveException extends RuntimeException {
        public AdvertisementSaveException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
