package com.example.gongdon.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class Detail {

    @NotNull
    private String name;

    @NotNull
    private String url;
}
