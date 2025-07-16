package com.sandeep.shoplite.repository;

import com.sandeep.shoplite.entity.ProductDescriptionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionPointRepository extends JpaRepository<ProductDescriptionPoint, Long> {
}
