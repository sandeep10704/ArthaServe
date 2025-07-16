

package com.sandeep.shoplite.repository;
import com.sandeep.shoplite.entity.BlogGadget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogGadgetRepository extends JpaRepository<BlogGadget, Long> {
}
