package com.example.gongdon.user.controller;

import com.example.gongdon.user.dto.SigninRequest;
import com.example.gongdon.user.dto.SignupRequest;
import com.example.gongdon.user.dto.UserDto;
import com.example.gongdon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/signup")
    public UserDto signup(@RequestBody @Valid SignupRequest req, Errors errors) {
        if (errors.hasErrors()) {
            return null;
        }

        return userService.signUp(req);
    }

    @PostMapping("/api/user/signin")
    public UserDto signin(@RequestBody @Valid SigninRequest req, Errors errors) {
        if (errors.hasErrors()) {
            return null;
        }

        return userService.signIn(req);
    }
}
