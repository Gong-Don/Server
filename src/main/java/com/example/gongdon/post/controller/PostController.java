package com.example.gongdon.post.controller;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.request.CreateRequest;
import com.example.gongdon.post.dto.response.DetailResponse;
import com.example.gongdon.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value= "/api/post")
@RestController
public class PostController {

    private final PostService postService;

    @ApiOperation(value="글 보기", notes="매칭되지 않은 모든 글 반환")
    @GetMapping("/all")
    public List<Post> postList() {
        log.info("매칭되지 않은 글 보기 요청");

        return postService.lists();
    }

    @ApiOperation(value="카테고리별 글 보기", notes="카테고리에 해당하는 글 반환")
    @GetMapping("/category/{category}")
    public List<Post> postCategoryList(@PathVariable("category") Category category) {
        log.info("카테고리에 해당하는 글 검색 요청");

        return postService.categoryLists(category);
    }

    @ApiOperation(value="제목으로 검색된 글 보기", notes="제목에 검색어를 포함하는 글 반환")
    @GetMapping("/title/{title}")
    public List<Post> postTitleList(@PathVariable("title") String title) {
        log.info("제목에 검색어를 포함하는 글 검색 요청");

        return postService.titleLists(title);
    }

    @ApiOperation(value="글 등록", notes="성공 시 code와 message 반환")
    @PostMapping()
    public SuccessResponse postAdd(@RequestBody @Valid CreateRequest request) {
        log.info("글 등록 요청");
        postService.create(request);

        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 등록되었습니다.");
    }

    @ApiOperation(value="글 하나 보기", notes="성공 시 해당 post 반환")
    @GetMapping("/{postId}")
    public DetailResponse postDetails(@PathVariable("postId") Long postId) {
        log.info("특정 글 보기 요청");

        return postService.detail(postId);
    }

    @ApiOperation(value="글 삭제", notes="성공 시 code와 message 반환")
    @DeleteMapping("/{postId}")
    public SuccessResponse postRemove(@PathVariable("postId") Long postId) {
        log.info("글 삭제 요청");
        postService.delete(postId);

        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 삭제되었습니다.");
    }

    @ApiOperation(value="글 수정", notes="성공 시 code와 message 반환")
    @PutMapping("/{postId}")
    public SuccessResponse postModify(@PathVariable("postId") Long postId, @RequestBody @Valid CreateRequest request) {
        log.info("글 수정 요청");
        postService.update(postId, request);

        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 수정되었습니다.");
    }
}
