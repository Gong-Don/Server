package com.example.gongdon.user.service;

import com.example.gongdon.user.domain.User;
import com.example.gongdon.user.dto.SigninRequest;
import com.example.gongdon.user.dto.SignupRequest;
import com.example.gongdon.user.dto.UserDto;
import com.example.gongdon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto signIn(SigninRequest userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            return null;
        }

        // 사용자 입력 비밀번호와 DB 저장된 사용자의 비밀번호 일치 여부 검사
        // 로그인 상태 저장 방식이 확정되면 수정
        if (user.getPassword().equals(userDto.getPassword())) {
            return new UserDto(user);
        }

        return null;
    }

    public UserDto signUp(SignupRequest userDto) {

        // Email 중복 검사
        User emailCheck = userRepository.findByEmail(userDto.getEmail());

        if (emailCheck != null) {
            return null;
        }

        // Name 중복 검사
        User nameCheck = userRepository.findByName(userDto.getName());

        if (nameCheck != null) {
            return null;
        }

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .build();

        userRepository.save(user);
        return new UserDto(user);
    }

}
