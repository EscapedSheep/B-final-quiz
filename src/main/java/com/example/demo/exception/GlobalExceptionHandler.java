package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdNotExistedException.class)
    public ResponseEntity<Error> handleIdNotExisted(IdNotExistedException exception) {
        Error error = Error.builder()
                .message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
