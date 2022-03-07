package com.example.gongdon.post.repository;

import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.Request.CreateRequest;

import java.util.List;
import java.util.Optional;

public interface PostCutsomRepository {
    List<Post> findAll();
    Optional<List<Post>> findByTitle(String searchStr);
    Optional<List<Post>> findByCategory(Category searchCate);
}
