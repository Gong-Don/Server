package com.example.gongdon.user.service;

import com.example.gongdon.errors.AlreadyExistEmailException;
import com.example.gongdon.errors.AlreadyExistNameException;
import com.example.gongdon.errors.NotExistUserException;
import com.example.gongdon.errors.NotMatchPasswordException;
import com.example.gongdon.user.domain.User;
import com.example.gongdon.user.dto.SigninRequest;
import com.example.gongdon.user.dto.SignupRequest;
import com.example.gongdon.user.dto.UserDto;
import com.example.gongdon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto signUp(SignupRequest userDto) {

        // DB에 해당 Email 을 가진 사용자 조회
        if(userRepository.findByEmail(userDto.getEmail()).isPresent())
            throw new AlreadyExistEmailException();

        // DB에 해당 Name 을 가진 사용자 조회
        if(userRepository.findByName(userDto.getName()).isPresent())
            throw new AlreadyExistNameException();

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .build();

        userRepository.save(user);
        return new UserDto(user);
    }

    @Transactional
    public UserDto signIn(SigninRequest userDto) {
        // DB에 해당 Email 을 가진 사용자 조회
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() ->
                new NotExistUserException());

        // 사용자 입력 비밀번호와 DB 저장된 사용자의 비밀번호 일치 여부 검사
        // 로그인 상태 저장 방식이 확정되면 수정
        if (!user.matchPassword(userDto.getPassword()))
            throw new NotMatchPasswordException();


        return new UserDto(user);
    }
}
