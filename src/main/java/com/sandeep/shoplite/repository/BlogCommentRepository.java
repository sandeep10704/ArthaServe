package com.sandeep.shoplite.repository;


import com.sandeep.shoplite.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
}
