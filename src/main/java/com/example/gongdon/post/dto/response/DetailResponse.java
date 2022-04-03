package com.example.gongdon.post.dto.response;

import com.example.gongdon.file.domain.File;
import com.example.gongdon.file.dto.Detail;
import com.example.gongdon.post.domain.Category;
import com.example.gongdon.post.domain.Post;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DetailResponse {

    @NotNull
    private Long wrtId;

    @NotBlank
    private String wrtName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotBlank
    private String title;

    @NotBlank
    @Lob
    private String content;

    @NotNull
    private int price;

    @NotNull
    private int likeCnt;

    @NotNull
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime date;

    @NotNull
    private List<String> tags;

    @NotNull
    private List<Detail> files = new ArrayList<>();

    public DetailResponse(Post post, List<String> tags) {
        this.wrtId = post.getWrtId();
        this.wrtName = post.getWrtName();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.price = post.getPrice();
        this.likeCnt = post.getLikeCnt();
        this.date = post.getDate();
        this.tags = tags;

        for(File file : post.getFiles())
            this.files.add(new Detail(file.getName(), file.getUrl()));
    }
}
