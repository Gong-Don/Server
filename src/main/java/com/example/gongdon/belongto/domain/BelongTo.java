package com.example.gongdon.belongto.domain;

import com.example.gongdon.post.domain.Post;
import com.example.gongdon.tag.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class BelongTo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public BelongTo(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }
}
