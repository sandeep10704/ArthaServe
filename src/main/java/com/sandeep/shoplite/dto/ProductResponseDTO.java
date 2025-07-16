package com.sandeep.shoplite.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;
    private ProductDetails product;
    private ProductDescription productDescription;
    private Set<ProductReview> reviews;

    @Getter
    @Setter
    public static class ProductDetails {
        private String name;
        private double price;
        private int rating;
        private String description;
        private Set<String> colors;
        private Set<String> sizes;
        private int stock;
        private String sku;
        private Set<String> category;
        private Set<String> tags;
        private String Brands;
        private Set<String> images;
    }

    @Getter
    @Setter
    public static class ProductDescription {
        private String title;
        private String topText;
        private Set<String> points;
        private String bottomText;
    }

    @Getter
    @Setter
    public static class ProductReview {
        private String image;
        private String name;
        private String date;
        private String text;
    }
}
