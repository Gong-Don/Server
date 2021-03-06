package com.example.gongdon.post.service;

import com.example.gongdon.belongto.service.BelongToService;
import com.example.gongdon.errors.exception.PostNotFoundException;
import com.example.gongdon.file.domain.File;
import com.example.gongdon.file.service.FileService;
import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.request.CreateRequest;
import com.example.gongdon.post.dto.response.DetailResponse;
import com.example.gongdon.post.repository.PostRepository;
import com.example.gongdon.belongto.domain.BelongTo;
import com.example.gongdon.tag.service.TagService;
import com.example.gongdon.user.domain.User;
import com.example.gongdon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
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
    private final FileService fileService;

    @Transactional
    public void create(CreateRequest request, List<MultipartFile> files) {

        User user = userService.findWriter(request.getWrtId());

        log.info("create(), username : {}", user.getName());

        Post post = new Post(request.getWrtId(), user.getName(), request.getCategory(), request.getTitle(), request.getContent(), request.getPrice());

        postRepository.save(post);

        uploadFiles(files, post);

        tagService.create(request.getTags(), post);
    }

    @Transactional
    public void delete(Long postId) {
        log.info("delete(), postId : {}", postId);

        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        tagService.delete(belongToService.deleteTags(post));
        // postRepository.delete(post);
    }

    @Transactional
    public void update(Long postId, CreateRequest request) {
        // TODO TAG & File??? ????????? Cascade ????????? ????????? ???????????? ??????
        log.info("update(), postId : {}", postId);

        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.save(post.update(request.getCategory(), request.getTitle(), request.getContent(), request.getPrice()));
    }

    @Transactional
    public List<Post> lists() {
        return postRepository.getPosts();
    }

    @Transactional
    public DetailResponse detail(Long postId) {
        log.info("detail(), postId : {}", postId);

        if (!postRepository.existsById(postId)) throw new PostNotFoundException();

        return new DetailResponse(postRepository.findPostByPostId(postId) ,getTagList(belongToService.findByPost(postRepository.findPostByPostId(postId))));
    }

    @Transactional
    public List<Post> titleLists(String title) {
        log.info("titleLists(), title : {}", title);

        return postRepository.getPostsByTitle(title);
    }

    @Transactional
    public List<Post> categoryLists(Category category) {
        log.info("categoryLists(), category : {}", category);

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

    private void uploadFiles(List<MultipartFile> files, Post post) {
        if (files != null && !files.isEmpty()) {
            log.info("postId= {} update files", post.getPostId());

            for (MultipartFile file : files)
                post.addFile(new File(file.getName(), fileService.upload(file, "post/" + post.getPostId())));
        }
    }
}
