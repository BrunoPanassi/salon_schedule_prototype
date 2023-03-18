package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepo extends JpaRepository<Manager, Long> {
    @Query(value = """
            SELECT
            m
            FROM Manager m
            JOIN m.person p
            where p.id = :person_id
            """)
    Manager findManagerByPersonId(@Param("person_id") Long personId);
}
