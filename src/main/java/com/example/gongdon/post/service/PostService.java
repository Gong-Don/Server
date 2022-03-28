package com.example.gongdon.post.service;

import com.example.gongdon.belongto.service.BelongToService;
import com.example.gongdon.errors.exception.PostNotFoundException;
import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.request.CreateRequest;
import com.example.gongdon.post.dto.response.DetailResponse;
import com.example.gongdon.post.repository.PostRepository;
import com.example.gongdon.belongto.domain.BelongTo;
import com.example.gongdon.belongto.repository.BelongToRepository;
import com.example.gongdon.tag.service.TagService;
import com.example.gongdon.user.domain.User;
import com.example.gongdon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final TagService tagService;
    private final BelongToService belongToService;

    @Transactional
    public void create(CreateRequest request) {

        User user = userService.findWriter(request.getWrtId());

        log.info("Post 생성 요청, 사용자 이름 : {}", user.getName());

        Post post = new Post(request.getWrtId(), user.getName(), request.getCategory(), request.getTitle(), request.getContent(), request.getPrice());

        postRepository.save(post);

        tagService.create(request.getTags(), post);
    }

    @Transactional
    public void delete(Long postId) {
        log.info("Post 삭제 요청");

        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }

    @Transactional
    public void update(Long postId, CreateRequest request) {
        // TODO TAG가 추가되면 여기서도 코드 추가
        log.info("Post 수정 요청");

        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.save(post.update(request.getCategory(), request.getTitle(), request.getContent(), request.getPrice()));
    }

    @Transactional
    public List<Post> lists() {
        log.info("Post 보기 요청");

        return postRepository.getPosts();
    }

    @Transactional
    public DetailResponse detail(Long postId) {
        log.info("특정 Post 보기 요청");

        if (!postRepository.existsById(postId)) throw new PostNotFoundException();

        return new DetailResponse(postRepository.findPostByPostId(postId) ,getTagList(belongToService.findByPost(postRepository.findPostByPostId(postId))));
    }

    @Transactional
    public List<Post> titleLists(String title) {
        log.info("Post 제목 검색 요청");

        return postRepository.getPostsByTitle(title);
    }

    @Transactional
    public List<Post> categoryLists(Category category) {
        log.info("Post 카테고리 검색 요청");

        return postRepository.findPostByCategory(category, Sort.by(Sort.Direction.DESC, "date"));
    }

    //
    // private methods
    //

    private List<String> getTagList(List<BelongTo> belongTos) {

        List<String> tags = new ArrayList<>();

        for (BelongTo belongTo : belongTos)
            tags.add(belongTo.getTag().getName());

        return tags;
    }
}
