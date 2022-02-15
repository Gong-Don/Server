package com.example.gongdon.user.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String email;
    private String password;

    public SigninRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}