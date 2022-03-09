package com.example.gongdon.post.repository;

import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;

import java.util.List;

public interface PostCustomRepository {
    List<Post> getPosts();
    List<Post> getPostsByTitle(String title);
}
