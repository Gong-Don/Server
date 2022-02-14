package com.example.gongdon.user.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public SignupRequest(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
