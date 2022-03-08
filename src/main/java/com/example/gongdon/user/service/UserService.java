package com.example.gongdon.user.service;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.errors.exception.*;
import com.example.gongdon.user.domain.Token;
import com.example.gongdon.user.domain.User;
import com.example.gongdon.user.dto.Request.*;
import com.example.gongdon.user.dto.Response.EmailAuthResponse;
import com.example.gongdon.user.dto.Response.SigninResponse;
import com.example.gongdon.user.repository.TokenRepository;
import com.example.gongdon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    @Transactional
    public SuccessResponse signUp(SignupRequest request) {

        // DB에 해당 Name 을 가진 사용자 조회
        if(userRepository.findByName(request.getName()).isPresent())
            throw new AlreadyExistNameException();

        // 클라이언트에서 보낸 토큰아이디가 유효한지를 검사
        Token token = tokenRepository.findById(request.getTokenId()).orElseThrow(InvalidTokenException::new);

        // 이메일 인증이 완료되었는지를 확인
        if(!token.isExpired())
            throw new InvalidTokenException();

        User user = new User(request.getName(), request.getEmail(), request.getPassword());

        userRepository.save(user);

        return SuccessResponse.of(HttpStatus.OK, "회원가입이 정상적으로 처리되었습니다.");
    }

    @Transactional
    public SigninResponse signIn(SigninRequest request) {

        // DB에 해당 Email 을 가진 사용자 조회
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(NotExistUserException::new);

        // 사용자 입력 비밀번호와 DB 저장된 사용자의 비밀번호 일치 여부 검사
        // 로그인 상태 저장 방식이 확정되면 수정
        if (!user.matchPassword(request.getPassword()))
            throw new NotMatchPasswordException();

        return new SigninResponse(user.getUserId());
    }

    @Transactional
    public EmailAuthResponse emailAuth(EmailAuthRequest request) {

        // DB에 해당 Email 을 가진 사용자 조회
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new AlreadyExistEmailException();

        // tokenId를 반환
        return new EmailAuthResponse(tokenService.createEmailConfirmationToken(request.getEmail()));
    }

    @Transactional
    public String confirmEmail(String tokenId) {

        // 사용자가 이메일로 보내진 링크를 클릭했을때 토큰의 유효성을 검사한후 결과 리턴
        Optional<Token> token = tokenRepository.findById(tokenId);

        if(token.isEmpty())
            return "/AuthFailed.html";

        // 시간이 만료되었는지 아닌지
        if (token.get().isCheckExpired())
            return "/AuthFailed.html";

        token.get().usedToken();
        tokenRepository.save(token.get());

        return "/AuthSuccess.html";
    }

    @Transactional
    public SuccessResponse updateName(UpdateNameRequest request) {

        // DB에 해당 Name 을 가진 사용자 조회
        if(userRepository.findByName(request.getName()).isPresent())
            throw new AlreadyExistNameException();

        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(NotExistUserException::new);

        user.updateName(request.getName());
        userRepository.save(user);

        return SuccessResponse.of(HttpStatus.OK, "닉네임이 변경되었습니다.");
    }

    @Transactional
    public SuccessResponse updatePassword(UpdatePasswordRequest request) {

        User user = userRepository.findByUserId(request.getUserId()).orElseThrow(NotExistUserException::new);

        // 현재 비밀번호와 새로운 비밀번호가 같은지 검사
        if(user.matchPassword(request.getPassword()))
            throw new SamePasswordException();

        user.updatePassword(request.getPassword());
        userRepository.save(user);

        return SuccessResponse.of(HttpStatus.OK, "비밀번호가 변경되었습니다.");
    }
}
