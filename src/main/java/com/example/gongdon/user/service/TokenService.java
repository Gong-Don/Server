package com.example.gongdon.user.service;

import com.example.gongdon.user.domain.Token;
import com.example.gongdon.user.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    // 이메일 인증 토큰 생성
    public String createToken(String receiverEmail) {
        Token token = Token.create(receiverEmail);
        tokenRepository.save(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("GONG-DON 회원가입 이메일 인증");
        mailMessage.setText("http://localhost:8080/api/user/auth?tokenId=" + token.getId());
        emailService.sendEmail(mailMessage);

        return token.getId();
    }
}
