package com.sandeep.shoplite.dto;

import lombok.Data;

@Data
public class ProductReviewDTO {
    private String reviewerName;
    private String reviewDate; // Can use LocalDate if needed
    private String text;
    private String imageUrl;
}
