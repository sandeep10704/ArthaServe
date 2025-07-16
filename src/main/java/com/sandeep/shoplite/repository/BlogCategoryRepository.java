package com.sandeep.shoplite.repository;

import com.sandeep.shoplite.entity.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
}
