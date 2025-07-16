package com.sandeep.shoplite.controllers;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/home")
public class HomeControllers {

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
}