package com.example.gongdon.tag.service;

import com.example.gongdon.belongto.service.BelongToService;
import com.example.gongdon.post.domain.Post;
import com.example.gongdon.belongto.domain.BelongTo;
import com.example.gongdon.tag.domain.Tag;
import com.example.gongdon.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final BelongToService belongToService;

    @Transactional
    public Map<String, Integer> lists() {
        return getTagMap();
    }

    @Transactional
    public void create(List<String> tags, Post post) {
        if (tags != null  && !tags.isEmpty())
            for (String tagName : tags) {
                Optional<Tag> tag = tagRepository.findByName(tagName);

                // Tag 가 없으면 새로 생성
                if(tag.isEmpty())
                    tagRepository.save(new Tag(tagName));

                // belongto 에 추가
                belongToService.save(new BelongTo(post, tagRepository.findByName(tagName).get()));
            }
    }

    private Map<String, Integer> getTagMap() {
        Map<String, Integer> tags = new HashMap<>();

        for (BelongTo belongTo : belongToService.findAll())
            tags.put(belongTo.getTag().getName(), tags.getOrDefault(belongTo.getTag().getName(), 0) + 1);

        // tag의 개수로 정렬하여 list_entries에 담김
        List<Map.Entry<String, Integer>> list_entries = new ArrayList<>(tags.entrySet());
        list_entries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        // 정렬된 tags를 삽입 시 순서가 보장되는 LinkedHashMap에 저장 후 반환
        Map<String, Integer> sortedTags = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list_entries) {
            log.info(entry.getKey() + " : " + entry.getValue());
            sortedTags.put(entry.getKey(), entry.getValue());
        }

        return sortedTags;
    }
}
