package com.example.gongdon.tag.repository;

import com.example.gongdon.post.domain.Post;
import com.example.gongdon.tag.domain.BelongTo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BelongToRepository extends JpaRepository<BelongTo, Long> {
    List<BelongTo> findByPost(Post post);
}
