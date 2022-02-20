package com.example.gongdon.user.dto;

import com.example.gongdon.user.domain.User;
import lombok.Data;

@Data
public class UserDto {
    private Long userId;

    private String name;
    private String email;
    private String password;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
