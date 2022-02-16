package com.example.gongdon.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private int code;
    private String message;

    public static ErrorResponse of (HttpStatus status, String message ) {
        return new ErrorResponse(status.value(), message);
    }
}