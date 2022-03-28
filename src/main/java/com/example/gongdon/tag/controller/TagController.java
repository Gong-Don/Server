package com.example.gongdon.tag.controller;


import com.example.gongdon.tag.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/tag")
@Controller
public class TagController {

    private final TagService tagService;

    @ApiOperation(value="태그 목록 보기", notes="태그를 map 형식으로 반환")
    @GetMapping("/all")
    @ResponseBody
    public Map<String, Integer> tagList() {
        log.info("태그를 map 형식으로 반환");

        return tagService.lists();
    }
}
