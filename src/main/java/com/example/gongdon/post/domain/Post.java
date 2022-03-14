package com.example.gongdon.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    private Long wrtId;

    private String wrtName;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    @Lob
    private String content;

    private int price;

    private int likeCnt = 0;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime date;

    // 외주 매칭이 완료되면, status(상태)를 TRUE로 변경
    private boolean matchingStatus = false;

    public Post (Long wrtId, String wrtName, Category category, String title, String content, int price) {
        this.wrtId = wrtId;
        this.wrtName = wrtName;
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
        if (this.likeCnt > 0) this.likeCnt--;
    }

    public Post update(Category category, String title, String content, int price) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.price = price;

        return this;
    }
}
