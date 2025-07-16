package com.sandeep.shoplite.repository;

import com.sandeep.shoplite.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.images " +
            "LEFT JOIN FETCH p.reviews " +
            "LEFT JOIN FETCH p.productDescription pd " +
            "LEFT JOIN FETCH pd.points " +
            "LEFT JOIN FETCH p.categories " +
            "LEFT JOIN FETCH p.colors " +
            "LEFT JOIN FETCH p.sizes " +
            "LEFT JOIN FETCH p.tags " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithAll(@Param("id") Long id);
}
