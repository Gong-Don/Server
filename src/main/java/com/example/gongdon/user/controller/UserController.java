package com.example.gongdon.user.controller;

import com.example.gongdon.user.dto.Request.EmailAuthRequest;
import com.example.gongdon.user.dto.Request.SigninRequest;
import com.example.gongdon.user.dto.Request.SignupRequest;
import com.example.gongdon.user.dto.Response.EmailAuthResponse;
import com.example.gongdon.user.dto.Response.SigninResponse;
import com.example.gongdon.user.dto.Response.SignupResponse;
import com.example.gongdon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/signup")
    public SignupResponse signup(@RequestBody @Valid SignupRequest req) {
        return userService.signUp(req);
    }

    @PostMapping("/api/user/signin")
    public SigninResponse signin(@RequestBody @Valid SigninRequest req) {
        return userService.signIn(req);
    }

    @PostMapping("/api/user/auth")
    public EmailAuthResponse emailAuth(@RequestBody @Valid EmailAuthRequest req) {
        return userService.emailAuth(req);
    }

    @GetMapping("/api/user/auth")
    public String confirmEmail(@RequestParam String tokenId) {
        return userService.confirmEmail(tokenId);
    }
}
