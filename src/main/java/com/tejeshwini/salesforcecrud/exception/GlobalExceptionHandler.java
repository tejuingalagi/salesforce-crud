package com.tejeshwini.salesforcecrud.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalState(
            IllegalStateException ex) {

        String message =
                ex.getMessage() == null ? "Unexpected error" : ex.getMessage();

        HttpStatus status = HttpStatus.BAD_GATEWAY;

        // Not logged in, or Salesforce says the access token is invalid/expired
        if (message.startsWith("Salesforce authentication required")
                || message.contains("Status: 401")) {
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.status(status)
                .body(Map.of("message", message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(
            IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }
}