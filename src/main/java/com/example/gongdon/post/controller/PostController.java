package com.example.gongdon.post.controller;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.post.dto.Request.CreateRequest;
import com.example.gongdon.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(value="회원가입", notes="회원가입 클릭 시 code와 message 반환")
    @PostMapping("/api/post/create")
    public SuccessResponse create(CreateRequest request) {
        return postService.create(request);
    }
}
