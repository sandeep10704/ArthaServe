package com.sandeep.shoplite.repository;


import com.sandeep.shoplite.entity.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Integer> {
}
