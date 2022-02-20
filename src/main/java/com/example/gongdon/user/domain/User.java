package com.example.gongdon.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Builder
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = UserType.ENTERPRISE;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
