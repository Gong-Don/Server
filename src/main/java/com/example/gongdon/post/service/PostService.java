package com.example.gongdon.post.service;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.post.dto.Request.CreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    @Transactional
    public SuccessResponse create(CreateRequest request) {
        // TAG가 추가되면, TAG가 비었는지 확인하는 로직 필요함

        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 등록되었습니다.");
    }
}
