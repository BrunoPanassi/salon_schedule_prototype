package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepo extends JpaRepository<Client, Long> {
    @Query(value = """
            SELECT
            c
            FROM Client c
            JOIN c.person p
            where p.id = :person_id
            """)
    Client findClientByPersonId(@Param("person_id") Long personId);
}
