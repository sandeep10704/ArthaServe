package com.sandeep.shoplite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_reviews")
@Getter
@Setter
public class CustomerReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "review_text", nullable = false, columnDefinition = "TEXT")
    private String reviewText;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Integer stars;
}
