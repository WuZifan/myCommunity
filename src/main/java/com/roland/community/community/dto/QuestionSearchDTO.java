package com.roland.community.community.dto;

import lombok.Data;

@Data
public class QuestionSearchDTO {
    private String search;
    private Integer offset;
    private Integer limit;
}
