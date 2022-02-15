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

//    public UserDto singIn(SigninRequest userDto) {
//
//
//        return new UserDto();
//    }

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
