package com.example.gongdon.post.repository;

import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;

import java.util.List;

public interface PostRepositoryCutsom {
    List<Post> findByTitle(String title);
    List<Post> findByCategory(Category category);
}
