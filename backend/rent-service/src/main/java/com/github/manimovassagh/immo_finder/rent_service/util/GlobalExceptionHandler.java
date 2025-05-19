package com.github.manimovassagh.immo_finder.rent_service.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static <T> ResponseEntity<ApiResponse<T>> errorResponseEntity(String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>("ERROR", message, null);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return errorResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ApiResponse<T>> handleException(Exception ex) {
        return errorResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 