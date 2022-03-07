package com.example.gongdon.post.dto.Request;

import com.example.gongdon.post.domain.Category;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class CreateRequest {

    @NotBlank(message = "작성자는 필수 입력 값입니다.")
    private Long wrtId;

    @NotBlank(message = "카테고리는 필수 입력 값입니다.")
    private Category category;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    @NotBlank(message = "가격은 필수 입력 값입니다.")
    private int price;
}
