package com.example.gongdon.post.controller;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.Request.CreateRequest;
import com.example.gongdon.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping(value= "/api/post")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ApiOperation(value="글 보기", notes="매칭되지 않은 모든 글 반환")
    @GetMapping("/all")
    public List<Post> showPost() {
        log.info("매칭되지 않은 글 보기 요청");
        return postService.showAll();
    }

    @ApiOperation(value="카테고리별 글 보기", notes="카테고리에 해당하는 글 반환")
    @GetMapping("/category/{category}")
    public List<Post> showCategoryPost(@PathVariable("category") Category category) {
        log.info("카테고리에 해당하는 글 검색 요청");
        return postService.getPostsByCategory(category);
    }

    @ApiOperation(value="제목으로 검색된 글 보기", notes="제목에 검색어를 포함하는 글 반환")
    @GetMapping("/title/{title}")
    public List<Post> showTitlePost(@PathVariable("title") String title) {
        log.info("제목에 검색어를 포함하는 글 검색 요청");
        return postService.getPostsByTitle(title);
    }

    @ApiOperation(value="글 등록", notes="성공 시 code와 message 반환")
    @PostMapping()
    public SuccessResponse createPost(@RequestBody @Valid CreateRequest request) {
        log.info("글 등록 요청");
        return postService.create(request);
    }

    @ApiOperation(value="글 삭제", notes="성공 시 code와 message 반환")
    @DeleteMapping("/{postId}")
    public SuccessResponse deletePost(@PathVariable("postId") Long postId) {
        log.info("글 삭제 요청");
        return postService.delete(postId);
    }

    @ApiOperation(value="글 수정", notes="성공 시 code와 message 반환")
    @PutMapping("/{postId}")
    public SuccessResponse updatePost(@PathVariable("postId") Long postId, CreateRequest request) {
        log.info("글 수정 요청");
        return postService.update(postId, request);
    }
}
