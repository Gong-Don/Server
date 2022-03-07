package com.example.gongdon.post.domain;

import com.example.gongdon.post.dto.Request.CreateRequest;
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

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    private String content;

    private int price;

    private int likeCnt = 0;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime date;

    // 외주 매칭이 완료되면, status(상태)를 TRUE로 변경
    private boolean matchingStatus = false;

    public Post(CreateRequest request) {
        this.wrtId = request.getWrtId();
        this.category = request.getCategory();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.price = request.getPrice();
    }

    public void matchingComplete() {
        this.matchingStatus = true;
    }

    public void incLikeCnt() {
        this.likeCnt++;
    }

    public void desLikeCnt() {
        this.likeCnt--;
    }
}
