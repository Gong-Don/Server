package com.example.gongdon.user.service;

import com.example.gongdon.errors.exception.*;
import com.example.gongdon.user.domain.Token;
import com.example.gongdon.user.domain.User;
import com.example.gongdon.user.dto.Request.*;
import com.example.gongdon.user.dto.Response.EmailAuthResponse;
import com.example.gongdon.user.dto.Response.SigninResponse;
import com.example.gongdon.user.repository.TokenRepository;
import com.example.gongdon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignupRequest request) {

        // DB에 해당 Email 을 가진 사용자 조회
        isAlreadyExistEmail(request.getEmail());
        // DB에 해당 Name 을 가진 사용자 조회
        isAlreadyExistName(request.getName());
        // 클라이언트에서 보낸 토큰아이디가 유효하고 이메일 인증이 완료되었는지를 확인
        isVerificationToken(request.getTokenId());

        userRepository.save(new User(request.getName(), request.getEmail(), request.getPassword()));
        log.info("user <" + request.getName() + "> is inserted");
    }

    @Transactional
    public SigninResponse signIn(SigninRequest request) {

        // DB에 해당 Email 을 가진 사용자 조회
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(NotExistUserException::new);
        // 사용자 입력 비밀번호와 DB 저장된 사용자의 비밀번호 일치 여부 검사
        isMatchingPassword(user, request.getPassword());

        return new SigninResponse(user.getUserId());
    }

    @Transactional
    public EmailAuthResponse emailAuth(EmailAuthRequest request) {

        // DB에 해당 Email 을 가진 사용자 조회
        isAlreadyExistEmail(request.getEmail());
        // tokenId를 반환
        return new EmailAuthResponse(tokenService.createToken(request.getEmail()));
    }

    @Transactional
    public String confirmEmail(String tokenId) {

        // 사용자가 이메일로 보내진 링크를 클릭했을때 토큰의 유효성을 검사한후 결과 리턴
        Optional<Token> token = tokenService.find(tokenId);

        if(!isValidToken(token))
            return "/AuthFailed.html";

        token.get().usedToken();
        tokenService.save(token.get());

        return "/AuthSuccess.html";
    }

    @Transactional
    public void modifyName(UpdateNameRequest request) {

        // DB에 해당 Name 을 가진 사용자 조회
        isAlreadyExistName(request.getName());

        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(NotExistUserException::new);

        user.updateName(request.getName());
        userRepository.save(user);
        log.info("user <" + user.getName() + ">'name is updated");
    }

    @Transactional
    public void modifyPassword(UpdatePasswordRequest request) {

        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(NotExistUserException::new);

        // 현재 비밀번호와 새로운 비밀번호가 같은지 검사
        isSamePassword(user, request);
        user.updatePassword(request.getPassword());
        userRepository.save(user);
        log.info("user <" + user.getName() + ">'password is updated");
    }

    @Transactional
    public User findWriter(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(NotExistWriterException::new);
    }

    private void isAlreadyExistName(String name) {
        if(userRepository.findByName(name).isPresent()){
            log.error("AlreadyExistNameException: name <" + name + "> is already exist.");
            throw new AlreadyExistNameException();
        }
    }

    private void isAlreadyExistEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()){
            log.error("AlreadyExistEmailException: email <" + email + "> is already exist.");
            throw new AlreadyExistEmailException();
        }
    }

    private void isMatchingPassword(User user, String password) {
        if (!user.matchPassword(password)) {
            log.error("NotMatchPasswordException: password doesn't match");
            throw new NotMatchPasswordException();
        }
    }

    private void isVerificationToken(String tokenId) {
        Token token = tokenService.find(tokenId).orElseThrow(InvalidTokenException::new);
        if(!token.isVerification()) {
            log.error("InvalidTokenException: invalid token");
            throw new InvalidTokenException();
        }
    }

    private boolean isValidToken(Optional<Token> token) {
        return token.isPresent() && !token.get().checkExpired();
    }

    private void isSamePassword(User user, UpdatePasswordRequest request) {
        if(user.matchPassword(request.getPassword())) {
            log.error("SamePasswordException: password is the same as before");
            throw new SamePasswordException();
        }
    }
}
