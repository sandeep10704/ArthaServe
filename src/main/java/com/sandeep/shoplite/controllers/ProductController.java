package com.sandeep.shoplite.controllers;

import com.sandeep.shoplite.dto.*;

import com.sandeep.shoplite.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private com.sandeep.shoplite.services.ProductService productService;

    @GetMapping
    public ProductPageResponseDTO getProductPage() {
        return productService.getProductPage();
    }


    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/{id}/reviews")
    public ProductReviewDTO addReview(@PathVariable Long id, @RequestBody ProductReviewDTO reviewDTO) {
        return productService.addReview(id, reviewDTO);
    }
    @PostMapping
    public ProductResponseDTO addFullProduct(@RequestBody ProductRequestDTO requestDTO) {
        return productService.addFullProduct(requestDTO);
    }
    @GetMapping("/bestselling")
    public List<ProductResponseDTO> getTop5BestSellingProducts() {
        return productService.getTop5BestSellingProducts();
    }
    @PostMapping("/selling")
    public ProductSellingDTO addOrUpdateSelling(@RequestBody ProductSellingDTO dto) {
        return productService.saveOrUpdateSelling(dto);
    }


}
