package com.yordanchorbadzhiyski.jobprocessing.controller;

import com.yordanchorbadzhiyski.jobprocessing.exceptions.CycleDetectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getClass(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CycleDetectedException.class)
    public ResponseEntity<String> handleException(CycleDetectedException ex) {
        return new ResponseEntity<>("DATA ERROR: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
