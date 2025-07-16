

package com.sandeep.shoplite.repository;
import com.sandeep.shoplite.entity.BlogSocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogSocialLinkRepository extends JpaRepository<BlogSocialLink, Long> {
}
