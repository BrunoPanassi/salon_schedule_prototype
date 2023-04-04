package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {
    @Query(value = """
            SELECT
            j
            FROM Job j
            where UPPER(j.description) LIKE UPPER(CONCAT('%', :description, '%'))
            """)
    List<Job> findJobsByDescription(@Param("description") String description);
}
