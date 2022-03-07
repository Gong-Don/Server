package com.example.gongdon.post.service;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.Request.CreateRequest;
import com.example.gongdon.post.repository.PostCustomRepositoryImpl;
import com.example.gongdon.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public SuccessResponse create(CreateRequest request) {
        // TAG가 추가되면, TAG가 비었는지 확인하는 로직 필요함
        Post post = new Post(request);
        postRepository.save(post);
        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 등록되었습니다.");
    }

    @Transactional
    public SuccessResponse delete(Long postId) {
        // 어떤 예외를 던질까
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        postRepository.delete(post);
        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 삭제되었습니다.");
    }

    @Transactional
    public SuccessResponse update(Long postId, CreateRequest request) {
        // 어떤 예외를 던질까
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // setter를 이용해서 작성자를 제외한 정보 수정 or 생성자를 이용해서 새 객체 생성 ..?
        post.setCategory(request.getCategory());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPrice(request.getPrice());

        postRepository.save(post);

        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 수정되었습니다.");
    }
}
