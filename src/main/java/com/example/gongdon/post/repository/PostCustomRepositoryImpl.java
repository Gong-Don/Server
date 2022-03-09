package com.example.gongdon.post.repository;

import com.example.gongdon.post.domain.Post;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.gongdon.post.domain.QPost.post;

public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public PostCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Post> getPosts() {
        // TODO matchingStatus가 false인 것만 가져오기
        return find().where(post.matchingStatus.eq(false)).fetch();
    }

    // contains 함수로 검색어 포함여부를 확인하고 반환
    @Override
    public List<Post> getPostsByTitle(String title) {
        return find().where(post.title.contains(title)).fetch();
    }

    private JPAQuery<Post> find(){
        return jpaQueryFactory
                .select(Projections.constructor(Post.class,
                        post.postId,
                        post.wrtId,
                        post.wrtName,
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
