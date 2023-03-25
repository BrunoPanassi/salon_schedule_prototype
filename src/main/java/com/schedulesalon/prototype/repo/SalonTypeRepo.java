package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.SalonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonTypeRepo extends JpaRepository<SalonType, Long> {

    @Query(value = """
            SELECT
            st
            FROM SalonType st
            where UPPER(st.description) LIKE UPPER(CONCAT('%', :description, '%'))
            """)
    SalonType findSalonTypeByDescription(@Param("description") String description);

    @Query(value = """
            select
            st
            FROM SalonType st
            JOIN st.job j
            where UPPER(j.description) LIKE UPPER(CONCAT('%', :description, '%'))
            """)
    SalonType findSalonTypeByJobDescription(@Param("description") String description);
}
