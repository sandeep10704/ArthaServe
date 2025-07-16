package com.sandeep.shoplite.repository;

import com.sandeep.shoplite.dto.ProductResponseDTO;
import com.sandeep.shoplite.entity.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {
}
