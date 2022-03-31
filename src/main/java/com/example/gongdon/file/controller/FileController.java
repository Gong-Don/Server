package com.example.gongdon.file.controller;

import com.example.gongdon.file.service.FileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value= "/api/file")
@RestController
public class FileController {

    private final FileService fileService;

    @ApiOperation(value = "파일 추가", notes = "파일을 추가하면 해당 파일을 S3에 추가한 후 해당 파일의 URL 반환")
    @PostMapping("")
    public String fileAdd(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return fileService.upload(multipartFile);
    }

    @ApiOperation(value = "파일 삭제", notes = "해당 url 의 파일을 S3에서 제거")
    @DeleteMapping("")
    public void fileRemove(@RequestParam @Valid String url) {
        fileService.delete(url);
    }
}
