package com.example.gongdon.errors;

import com.example.gongdon.errors.exception.AlreadyExistEmailException;
import com.example.gongdon.errors.exception.AlreadyExistNameException;
import com.example.gongdon.errors.exception.NotExistUserException;
import com.example.gongdon.errors.exception.NotMatchPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyExistEmailException.class)
    ResponseEntity<ErrorResponse> handleAlreadyExistEmail(AlreadyExistEmailException ex) {
        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistNameException.class)
    ResponseEntity<ErrorResponse> handleAlreadyExistName(AlreadyExistNameException ex) {
        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistUserException.class)
    ResponseEntity<ErrorResponse> handleNotRegisteredUser(NotExistUserException ex) {
        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotMatchPasswordException.class)
    ResponseEntity<ErrorResponse> handleNotMatchPassword(NotMatchPasswordException ex) {
        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity handleException(Exception ex) {
        final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
