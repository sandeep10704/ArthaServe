package com.sandeep.shoplite.dto;

import lombok.Data;

@Data
public class BlogCommentDTO {
    private String name;
    private String date; // You can change to LocalDate if you store as DATE type
    private String text;
    private String avatar;
    private Long articleId;
}
