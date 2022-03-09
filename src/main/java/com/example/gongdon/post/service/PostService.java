package com.example.gongdon.post.service;

import com.example.gongdon.errors.SuccessResponse;
import com.example.gongdon.errors.exception.NotExistUserException;
import com.example.gongdon.errors.exception.PostNotFoundException;
import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.Request.CreateRequest;
import com.example.gongdon.post.repository.PostCustomRepositoryImpl;
import com.example.gongdon.post.repository.PostRepository;
import com.example.gongdon.user.domain.User;
import com.example.gongdon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public SuccessResponse create(CreateRequest request) {
        // TODO TAG가 추가되면, TAG가 비었는지 확인하는 로직
        // TODO NotExistUserException이 의미상 알맞은 것 같아서 사용 중인데 에러 메시지가 맞지 않는 문제
        Optional<User> user = Optional.ofNullable(userRepository.findByUserId(request.getWrtId()).orElseThrow(NotExistUserException::new));
        // user.ifPresent(value -> post.setWrtName(value.getName()));
        log.info("사용자 이름 : {}", user.get().getName());

        Post post = new Post(request.getWrtId(), user.get().getName(), request.getCategory(), request.getTitle(), request.getContent(), request.getPrice());
        postRepository.save(post);

        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 등록되었습니다.");
    }

    @Transactional
    public SuccessResponse delete(Long postId) {
        log.info("Post 삭제 요청");
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 삭제되었습니다.");
    }

    @Transactional
    public SuccessResponse update(Long postId, CreateRequest request) {
        log.info("Post 수정 요청");
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.save(updateSetting(post, request));
        return SuccessResponse.of(HttpStatus.OK, "글이 정상적으로 수정되었습니다.");
    }

    @Transactional
    public List<Post> showAll() {
        log.info("Post 보기 요청");
        return postRepository.getPosts();
    }

    @Transactional
    public List<Post> getPostsByTitle(String title) {
        log.info("Post 제목 검색 요청");
        return postRepository.getPostsByTitle(title);
    }

    @Transactional
    public List<Post> getPostsByCategory(Category category) {
        return postRepository.findPostByCategory(category);
    }

    private Post updateSetting(Post post, CreateRequest request) {
        post.setCategory(request.getCategory());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPrice(request.getPrice());

        return post;
    }
}
