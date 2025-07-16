package com.sandeep.shoplite.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDTO {
    private ProductResponseDTO.ProductDetails product;
    private ProductResponseDTO.ProductDescription productDescription;
    private List<ProductResponseDTO.ProductReview> reviews;
}
