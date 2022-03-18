package com.example.gongdon.tag.service;

import com.example.gongdon.tag.domain.BelongTo;
import com.example.gongdon.tag.repository.BelongToRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

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

        return tags;
    }
}
