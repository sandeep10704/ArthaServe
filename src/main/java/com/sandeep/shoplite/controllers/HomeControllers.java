package com.sandeep.shoplite.controllers;

import com.sandeep.shoplite.entity.CustomerReview;
import com.sandeep.shoplite.repository.CustomerReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*")
public class HomeControllers {

    @Autowired
    private CustomerReviewRepository reviewRepository;

    // Existing endpoints
    @GetMapping("/posters")
    public String getPosters() {
        return "Return all posters";
    }

    @GetMapping("/discount-posters")
    public String getDiscountPosters() {
        return "Return all discount posters";
    }

    @GetMapping("/categories")
    public String getCategories() {
        return "Return all categories";
    }

    @GetMapping("/best-selling")
    public String getBestSellingItems() {
        return "Return best selling items";
    }

    @GetMapping("/featured")
    public String getFeaturedItems() {
        return "Return featured items";
    }

    @GetMapping("/latest")
    public String getLatestItems() {
        return "Return latest items";
    }

    @GetMapping("/best-reviewed")
    public String getBestReviewedItems() {
        return "Return best reviewed items";
    }

    @GetMapping("/on-sale")
    public String getOnSaleItems() {
        return "Return on sale items";
    }

    // -------------------------------
    // Customer Reviews Endpoints
    // -------------------------------

    @GetMapping("/reviews")
    public List<CustomerReview> getAllReviews() {
        return reviewRepository.findAll();
    }

    @PostMapping("/reviews")
    public CustomerReview addReview(@RequestBody CustomerReview review) {
        return reviewRepository.save(review);
    }
}
