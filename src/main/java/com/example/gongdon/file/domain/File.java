package com.example.gongdon.file.domain;

import com.example.gongdon.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class File {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public File(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public void setPost(Post post) {
        this.post = post;

        // 게시글에 현재 파일이 존재하지 않는다면
        if(!post.getFiles().contains(this))
            // 파일 추가
            post.getFiles().add(this);
    }
}
