package com.example.gongdon.user.service;

import com.example.gongdon.errors.exception.AlreadyExistEmailException;
import com.example.gongdon.errors.exception.AlreadyExistNameException;
import com.example.gongdon.errors.exception.NotExistUserException;
import com.example.gongdon.errors.exception.NotMatchPasswordException;
import com.example.gongdon.user.domain.User;
import com.example.gongdon.user.dto.SigninRequest;
import com.example.gongdon.user.dto.SignupRequest;
import com.example.gongdon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long signUp(SignupRequest request) {

        // DB에 해당 Email 을 가진 사용자 조회
        if(userRepository.findByEmail(request.getEmail()).isPresent())
            throw new AlreadyExistEmailException();

        // DB에 해당 Name 을 가진 사용자 조회
        if(userRepository.findByName(request.getName()).isPresent())
            throw new AlreadyExistNameException();

        User user = new User(request.getName(), request.getEmail(), request.getPassword());

        userRepository.save(user);

        return user.getUserId();
    }

    @Transactional
    public Long signIn(SigninRequest request) {
        // DB에 해당 Email 을 가진 사용자 조회
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new NotExistUserException());

        // 사용자 입력 비밀번호와 DB 저장된 사용자의 비밀번호 일치 여부 검사
        // 로그인 상태 저장 방식이 확정되면 수정
        if (!user.matchPassword(request.getPassword()))
            throw new NotMatchPasswordException();


        return user.getUserId();
    }
}
