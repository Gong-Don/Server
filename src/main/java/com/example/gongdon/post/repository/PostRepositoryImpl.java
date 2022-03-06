package com.example.gongdon.post.repository;

import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCutsom {
    // 모든 글을 가져와서 contains 함수로 검색어 포함여부를 확인하고 반환
    @Override
    public List<Post> findByTitle(String title) {
        return null;
    }

    @Override
    public List<Post> findByCategory(Category category) {
        return null;
    }
}
