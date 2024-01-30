package com.yordanchorbadzhiyski.jobprocessing.controller;

import com.yordanchorbadzhiyski.jobprocessing.exceptions.CycleDetectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller advice class for handling exceptions globally within the application.
 * This class provides methods to handle specific exceptions and return appropriate
 * responses with error messages and HTTP status codes.
 *
 * The class is annotated with {@code @ControllerAdvice}, allowing it to be applied
 * globally to all controllers within the application.
 *
 * @author Yordan Chorbadzhiyski
 * @version 1.0
 * @since 2024-01-23
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Handles exceptions related to unreadable HTTP message content (e.g., invalid JSON format).
     *
     * @param ex The exception to be handled.
     * @return ResponseEntity containing an error message and HTTP status code (400 Bad Request).
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getClass(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions related to detected cycles in task dependencies.
     *
     * @param ex The exception to be handled.
     * @return ResponseEntity containing an error message and HTTP status code (400 Bad Request).
     */
    @ExceptionHandler(CycleDetectedException.class)
    public ResponseEntity<String> handleException(CycleDetectedException ex) {
        return new ResponseEntity<>("DATA ERROR: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
