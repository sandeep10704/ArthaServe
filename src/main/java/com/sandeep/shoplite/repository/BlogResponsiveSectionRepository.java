package com.sandeep.shoplite.repository;


import com.sandeep.shoplite.entity.BlogResponsiveSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogResponsiveSectionRepository extends JpaRepository<BlogResponsiveSection, Long> {
}
