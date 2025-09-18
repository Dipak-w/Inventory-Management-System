package com.example.inventory.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<com.example.inventory.exception.ApiError> handleAll(Exception ex, WebRequest request) {
        log.error("Unhandled error: {}", ex.getMessage());
        com.example.inventory.exception.ApiError err = new com.example.inventory.exception.ApiError(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error", "An unexpected error occurred", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<com.example.inventory.exception.ApiError> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ApiError err = new com.example.inventory.exception.ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                "Validation Failed", message, request.getDescription(false));
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<com.example.inventory.exception.ApiError> handleNotFound(ConfigDataResourceNotFoundException ex, WebRequest request) {
        com.example.inventory.exception.ApiError err = new com.example.inventory.exception.ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(),
                "Not Found", ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<com.example.inventory.exception.ApiError> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        com.example.inventory.exception.ApiError err = new ApiError(Instant.now(), HttpStatus.FORBIDDEN.value(),
                "Forbidden", "Access denied", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }
}