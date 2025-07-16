package com.sandeep.shoplite.repository;


import com.sandeep.shoplite.entity.BlogBottomParagraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogBottomParagraphRepository extends JpaRepository<BlogBottomParagraph, Long> {
}
