package com.example.gongdon.post.domain;

import com.example.gongdon.file.domain.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
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

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<File> files = new ArrayList<>();


    public Post (Long wrtId, String wrtName, Category category, String title, String content, int price) {
        this.wrtId = wrtId;
        this.wrtName = wrtName;
        this.category = category;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public Post(Long postId, Long wrtId, String wrtName, Category category, String title, String content, int price, int likeCnt, LocalDateTime date, boolean matchingStatus) {
        this.postId = postId;
        this.wrtId = wrtId;
        this.wrtName = wrtName;
        this.category = category;
        this.title = title;
        this.content = content;
        this.price = price;
        this.likeCnt = likeCnt;
        this.date = date;
        this.matchingStatus = matchingStatus;
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

    public void addFile(File file) {
        this.files.add(file);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(file.getPost() != this)
            // 파일 저장
            file.setPost(this);
    }
}
