package com.example.gongdon.post.repository;

import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
    List<Post> findPostByCategory(Category category);
    Post findPostByPostId(Long postId);
}
