package com.example.gongdon.post.dto.Request;

import com.example.gongdon.post.domain.Category;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateRequest {

    @NotNull(message = "작성자는 필수 입력 값입니다.")
    private Long wrtId;

    @NotNull(message = "카테고리는 필수 입력 값입니다.")
    private Category category;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price;
}
