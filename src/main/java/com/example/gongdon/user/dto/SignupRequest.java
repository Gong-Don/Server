package com.example.gongdon.user.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;

    public SignupRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
