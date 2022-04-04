package com.example.gongdon.belongto.service;

import com.example.gongdon.belongto.domain.BelongTo;
import com.example.gongdon.belongto.repository.BelongToRepository;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.tag.domain.Tag;
import com.example.gongdon.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public List<Tag> deleteTags(Post post) {
        List<Tag> lists = new ArrayList<>();
        for (BelongTo belongTo: findByPost(post)) {
            if (belongToRepository.findByTag(belongTo.getTag()).size() <= 1) {
                lists.add(belongTo.getTag());
            }
            belongToRepository.delete(belongTo);
        }
        return lists;
    }
}