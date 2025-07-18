package com.sandeep.shoplite.repository;


import com.sandeep.shoplite.entity.BlogArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogArticleRepository extends JpaRepository<BlogArticle, Long> {
    Optional<BlogArticle> findFirstByIdLessThanOrderByIdDesc(Long id);

    Optional<BlogArticle> findFirstByIdGreaterThanOrderByIdAsc(Long id);

}
