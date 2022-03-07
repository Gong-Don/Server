package com.example.gongdon.post.controller;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.Request.CreateRequest;
import com.example.gongdon.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(value="글 보기", notes="모든 글 JSON 배열로 반환")
    @GetMapping("/api/post/all")
    public List<Post> showPost() {
        // POST들을 JSON 배열로 매핑해서 반환해줘야 함, 그에 맞는 Response 클래스 필요
        return postService.showAll();
    }

    @ApiOperation(value="카테고리별 글 보기", notes="카테고리에 해당하는 글 반환")
    @GetMapping("/api/post/category/{category}")
    public List<Post> showCategoryPost(@PathVariable("category") Category category) {
        return postService.getPostsByCategory(category);
    }

    @ApiOperation(value="제목으로 검색된 글 보기", notes="검색어를 제목에 포함하는 글 반환")
    @GetMapping("/api/post/title/{title}")
    public List<Post> showTitlePost(@PathVariable("title") String title) {
        return postService.getPostsByTitle(title);
    }

    @ApiOperation(value="글 등록", notes="성공 시 code와 message 반환")
    @PostMapping("/api/post")
    public SuccessResponse createPost(@RequestBody @Valid CreateRequest request) {
        System.out.println("Request : " + request);
        return postService.create(request);
    }

    @ApiOperation(value="글 삭제", notes="성공 시 code와 message 반환")
    @DeleteMapping("/api/post/{postId}")
    public SuccessResponse deletePost(@PathVariable("postId") Long postId) {
        return postService.delete(postId);
    }

    @ApiOperation(value="글 수정", notes="성공 시 code와 message 반환")
    @PutMapping("/api/post/{postId}")
    public SuccessResponse updatePost(@PathVariable("postId") Long postId, CreateRequest request) {
        return postService.update(postId, request);
    }
}
