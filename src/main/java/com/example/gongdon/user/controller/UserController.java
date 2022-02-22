package com.example.gongdon.user.controller;

import com.example.gongdon.user.dto.SigninRequest;
import com.example.gongdon.user.dto.SignupRequest;
import com.example.gongdon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/signup")
    public Long signup(@RequestBody @Valid SignupRequest req) {
        return userService.signUp(req);
    }

    @PostMapping("/api/user/signin")
    public Long signin(@RequestBody @Valid SigninRequest req) {
        return userService.signIn(req);
    }
}
