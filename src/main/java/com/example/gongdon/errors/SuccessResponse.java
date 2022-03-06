package com.example.gongdon.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class SuccessResponse {
    private int code;
    private String message;

    public static SuccessResponse of (HttpStatus status, String message) {
        return new SuccessResponse(status.value(), message);
    }
}
