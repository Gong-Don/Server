package com.example.gongdon.belongto.repository;

import com.example.gongdon.post.domain.Post;
import com.example.gongdon.belongto.domain.BelongTo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BelongToRepository extends JpaRepository<BelongTo, Long> {
    List<BelongTo> findByPost(Post post);
}
