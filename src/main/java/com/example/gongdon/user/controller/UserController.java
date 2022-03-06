package com.example.gongdon.user.controller;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.user.dto.Request.*;
import com.example.gongdon.user.dto.Response.EmailAuthResponse;
import com.example.gongdon.user.dto.Response.SigninResponse;
import com.example.gongdon.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value="회원가입", notes="회원가입 클릭 시 code와 message 반환")
    @PostMapping("/api/user/signup")
    public SuccessResponse signup(@RequestBody @Valid SignupRequest req) {
        return userService.signUp(req);
    }

    @ApiOperation(value="로그인", notes="로그인 클릭 시 userId 반환")
    @PostMapping("/api/user/signin")
    public SigninResponse signin(@RequestBody @Valid SigninRequest req) {
        return userService.signIn(req);
    }

    @ApiOperation(value="이메일 인증", notes="이메일 인증 클릭 시 생성한 토큰 반환")
    @PostMapping("/api/user/auth")
    public EmailAuthResponse emailAuth(@RequestBody @Valid EmailAuthRequest req) {
        return userService.emailAuth(req);
    }

    @ApiOperation(value="이메일 인증 링크 클릭", notes="이메일로 보낸 링크 클릭 시 결과화면 반환")
    @GetMapping("/api/user/auth")
    public String confirmEmail(@RequestParam String tokenId) {
        return userService.confirmEmail(tokenId);
    }

    @ApiOperation(value="닉네임 변경", notes="수정 완료 클릭 시 code와 message 반환")
    @PostMapping("/api/user/name")
    public SuccessResponse updateName(@RequestBody @Valid UpdateNameRequest req) {
        return userService.updateName(req);
    }

    @ApiOperation(value="비밀번호 변경", notes="수정 완료 클릭 시 code와 message 반환")
    @PostMapping("/api/user/password")
    public SuccessResponse updatePassword(@RequestBody @Valid UpdatePasswordRequest req) {
        return userService.updatePassword(req);
    }
}
