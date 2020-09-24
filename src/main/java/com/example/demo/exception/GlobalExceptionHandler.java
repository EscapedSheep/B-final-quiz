package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdNotExistedException.class)
    public ResponseEntity<Error> handleIdNotExisted(IdNotExistedException exception) {
        Error error = Error.builder()
                .message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> paramValidationHandler(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        Error error = Error.builder()
                .message(message)
                .details(new HashMap<>()).build();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.addDetails(fieldError.getField(), fieldError.toString());
        });
        return ResponseEntity.badRequest().body(error);
    }
}
