package com.example.gongdon.user.service;

import com.example.gongdon.user.dto.Request.EmailAuthRequest;
import com.example.gongdon.user.dto.Response.EmailAuthResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;
    @Mock
    private TokenServiceTest tokenServiceTest;

    @Test
    @DisplayName("이메일 인증 성공")
    public void emailAuthTest() {
        // given
        EmailAuthRequest emailAuthRequest = new EmailAuthRequest();
        emailAuthRequest.setEmail("test@naver.com");

        // when
        EmailAuthResponse emailAuthResponse = userService.emailAuth(emailAuthRequest);
        // 왜 null이지
        System.out.println(emailAuthResponse);
        // then
        assertThat(emailAuthResponse.getAccessToken()).isNotEmpty();
    }

//    @Test
//    @DisplayName("회원가입 성공")
//    public void signup() {
//        // given
//        SignupRequest signupRequest = new SignupRequest("test", "test@naver.com", "!EEtest1234", "123");
//        Token token = Token.createToken("test@naver.com");
//        token.usedToken();
//
//        // when
//        Long id = userService.signUp(signupRequest);
//        System.out.println(id);
//        User user = userRepository.findUserByUserId(id);
//
//        // then
//        assertThat(signupRequest.getEmail()).isEqualTo(user.getEmail());
//    }
}