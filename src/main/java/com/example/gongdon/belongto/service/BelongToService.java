package com.example.gongdon.belongto.service;

import com.example.gongdon.belongto.domain.BelongTo;
import com.example.gongdon.belongto.repository.BelongToRepository;
import com.example.gongdon.post.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BelongToService {

    private final BelongToRepository belongToRepository;

    @Transactional
    public void save(BelongTo belongTo) {
        belongToRepository.save(belongTo);
    }

    @Transactional
    public List<BelongTo> findAll() {
        return belongToRepository.findAll();
    }

    @Transactional
    public List<BelongTo> findByPost(Post post) {
        return belongToRepository.findByPost(post);
    }
}
