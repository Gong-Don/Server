package com.example.gongdon.user.dto.Request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Data
public class EmailAuthRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email
    private String email;
}
