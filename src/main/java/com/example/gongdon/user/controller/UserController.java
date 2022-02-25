package com.example.gongdon.user.controller;

import com.example.gongdon.user.dto.EmailAuthRequest;
import com.example.gongdon.user.dto.SigninRequest;
import com.example.gongdon.user.dto.SignupRequest;
import com.example.gongdon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/user/auth")
    public String emailAuth(@RequestBody @Valid EmailAuthRequest req) {
        return userService.emailAuth(req);
    }

    @GetMapping("/api/user/auth")
    public String confirmEmail(@RequestParam String tokenId) {
        return userService.confirmEmail(tokenId);
    }
}
