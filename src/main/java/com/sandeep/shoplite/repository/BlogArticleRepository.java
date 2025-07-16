package com.sandeep.shoplite.repository;


import com.sandeep.shoplite.entity.BlogArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogArticleRepository extends JpaRepository<BlogArticle, Long> {
}
