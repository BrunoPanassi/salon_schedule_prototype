package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessionalRepo extends JpaRepository<Professional, Long> {
    @Query(value = """
            SELECT
            p
            FROM Professional p
            JOIN p.person p
            where p.id = :person_id
            """)
    Professional findProfessionalByPersonId(@Param("person_id") Long personId);
}
