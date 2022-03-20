package com.example.gongdon.tag.service;

import com.example.gongdon.tag.domain.BelongTo;
import com.example.gongdon.tag.domain.Tag;
import com.example.gongdon.tag.repository.BelongToRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TagService {

    private final BelongToRepository belongToRepository;

    @Transactional
    public HashMap<String, Integer> lists() {
        return getTagMap();
    }

    private HashMap<String, Integer> getTagMap() {
        HashMap<String, Integer> tags = new HashMap<>();

        for (BelongTo belongTo : belongToRepository.findAll())
            tags.put(belongTo.getTag().getName(), tags.getOrDefault(belongTo.getTag().getName(), 0) + 1);

        // tag의 개수로 정렬하여 list_entries에 담김
        List<Map.Entry<String, Integer>> list_entries = new ArrayList<>(tags.entrySet());
        list_entries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        // 정렬된 tags를 삽입 시 순서가 보장되는 LinkedHashMap에 저장 후 반환
        HashMap<String, Integer> sortedTags = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list_entries) {
            log.info(entry.getKey() + " : " + entry.getValue());
            sortedTags.put(entry.getKey(), entry.getValue());
        }

        return sortedTags;
    }
}
