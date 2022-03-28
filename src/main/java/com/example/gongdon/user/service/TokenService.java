package com.example.gongdon.user.service;

import com.example.gongdon.user.domain.Token;
import com.example.gongdon.user.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    // 이메일 인증 토큰 생성
    @Transactional
    public String createToken(String receiverEmail) {
        Token token = Token.create(receiverEmail);
        tokenRepository.save(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setSubject("GONG-DON 회원가입 이메일 인증");
        mailMessage.setText("http://choco-one.iptime.org:11104/api/user/auth?tokenId=" + token.getId());
        emailService.sendEmail(mailMessage);

        return token.getId();
    }

    @Transactional
    public Optional<Token> find(String tokenId) {
        return tokenRepository.findById(tokenId);
    }

    @Transactional
    public void save(Token token) {
        tokenRepository.save(token);
    }
}
