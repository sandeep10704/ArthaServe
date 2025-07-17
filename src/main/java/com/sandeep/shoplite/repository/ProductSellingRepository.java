package com.sandeep.shoplite.repository;

import com.sandeep.shoplite.entity.ProductSelling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductSellingRepository extends JpaRepository<ProductSelling, Long> {

    @Query("SELECT ps.productid FROM ProductSelling ps ORDER BY ps.sellingno DESC")
    List<Long> findTopSellingProductIds(Pageable pageable);


}
