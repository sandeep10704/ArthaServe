package com.sandeep.shoplite.repository;


import com.sandeep.shoplite.entity.BlogResponsiveParagraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogResponsiveParagraphRepository extends JpaRepository<BlogResponsiveParagraph, Long> {
}
