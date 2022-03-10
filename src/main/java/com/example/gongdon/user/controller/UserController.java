package com.example.gongdon.user.controller;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.user.dto.Request.*;
import com.example.gongdon.user.dto.Response.EmailAuthResponse;
import com.example.gongdon.user.dto.Response.SigninResponse;
import com.example.gongdon.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@Controller
public class UserController {

    private final UserService userService;

    @ResponseBody
    @ApiOperation(value="회원가입", notes="회원가입 클릭 시 code와 message 반환")
    @PostMapping("/signup")
    public SuccessResponse signup(@RequestBody @Valid SignupRequest req) {
        return userService.signUp(req);
    }

    @ResponseBody
    @ApiOperation(value="로그인", notes="로그인 클릭 시 userId 반환")
    @PostMapping("/signin")
    public SigninResponse signin(@RequestBody @Valid SigninRequest req) {
        return userService.signIn(req);
    }

    @ResponseBody
    @ApiOperation(value="이메일 인증", notes="이메일 인증 클릭 시 생성한 토큰 반환")
    @PostMapping("/auth")
    public EmailAuthResponse emailAuth(@RequestBody @Valid EmailAuthRequest req) {
        return userService.emailAuth(req);
    }

    @ApiOperation(value="이메일 인증 링크 클릭", notes="이메일로 보낸 링크 클릭 시 결과화면 반환")
    @GetMapping("/auth")
    public String emailConfirm(@RequestParam String tokenId) {
        return userService.confirmEmail(tokenId);
    }

    @ResponseBody
    @ApiOperation(value="닉네임 변경", notes="수정 완료 클릭 시 code와 message 반환")
    @PutMapping("/name")
    public SuccessResponse nameModify(@RequestBody @Valid UpdateNameRequest req) {
        return userService.modifyName(req);
    }

    @ResponseBody
    @ApiOperation(value="비밀번호 변경", notes="수정 완료 클릭 시 code와 message 반환")
    @PutMapping("/password")
    public SuccessResponse passwordModify(@RequestBody @Valid UpdatePasswordRequest req) {
        return userService.modifyPassword(req);
    }
}
