package com.example.gongdon.user.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class SignupResponse {
    private int code;
    private String message;

    public static SignupResponse of (HttpStatus status, String message) {
        return new SignupResponse(status.value(), message);
    }
}
