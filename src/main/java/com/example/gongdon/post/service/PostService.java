package com.example.gongdon.post.service;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.errors.exception.PostNotFoundException;
import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.Request.CreateRequest;
import com.example.gongdon.post.repository.PostCustomRepositoryImpl;
import com.example.gongdon.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public SuccessResponse create(CreateRequest request) {
        // TODO TAG가 추가되면, TAG가 비었는지 확인하는 로직
        Post post = new Post(request);
        postRepository.save(post);
        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 등록되었습니다.");
    }

    @Transactional
    public SuccessResponse delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 삭제되었습니다.");
    }

    @Transactional
    public SuccessResponse update(Long postId, CreateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        // setter를 이용해서 작성자를 제외한 정보 수정
        post.setCategory(request.getCategory());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPrice(request.getPrice());

        postRepository.save(post);

        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 수정되었습니다.");
    }

    @Transactional
    public List<Post> showAll() {
        return postRepository.getPosts();
    }

    @Transactional
    public List<Post> getPostsByTitle(String title) {
        return postRepository.getPostsByTitle(title);
    }

    @Transactional
    public List<Post> getPostsByCategory(Category category) {
        return postRepository.getPostsByCategory(category);
    }
}
