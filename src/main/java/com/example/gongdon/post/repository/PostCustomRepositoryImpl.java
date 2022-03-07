package com.example.gongdon.post.repository;

import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.post.dto.Request.CreateRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.gongdon.post.domain.QPost.post;

@Repository
public class PostCustomRepositoryImpl implements PostCutsomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Post> findAll() {
        return find().fetch();
    }

    // contains 함수로 검색어 포함여부를 확인하고 반환
    public Optional<List<Post>> findByTitle(String searchStr) {
        return Optional.ofNullable(find().where(post.title.contains(searchStr)).fetch());
    }

    // 카테고리명이 일치하는 post 반환
    @Override
    public Optional<List<Post>> findByCategory(Category searchCate) {
        return Optional.ofNullable(find().where(post.category.eq(searchCate)).fetch());
    }

    private JPAQuery<Post> find(){
        return jpaQueryFactory
                .select(Projections.constructor(Post.class,
                        post.postId,
                        post.wrtId,
                        post.category,
                        post.title,
                        post.content,
                        post.price,
                        post.likeCnt,
                        post.date,
                        post.matchingStatus
                )).from(post);
    }
}
