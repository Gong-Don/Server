package com.example.gongdon.post.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    private Long wrtId;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    private String content;

    private int price;

    private int likeCnt = 0;

    @CreatedDate
    private LocalDateTime date;

    // 외주 매칭이 완료되면, status(상태)를 TRUE로 변경
    private boolean matchingStatus = false;

    public Post(Long wrtId, Category category, String title, String content, int price) {
        this.wrtId = wrtId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public void matchingComplete() {
        this.matchingStatus = true;
    }

    public void incLikeCnt() {
        this.likeCnt++;
    }

    public void decLikeCnt() {
        this.likeCnt--;
    }
}
