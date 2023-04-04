package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Salon;
import com.schedulesalon.prototype.model.SalonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepo extends JpaRepository<Salon, Long> {

    @Query("""
            SELECT
            s
            FROM Salon s
            WHERE UPPER(s.name) LIKE CONCAT('%', UPPER(:name), '%')
            AND (:address is NULL OR UPPER(s.address) LIKE CONCAT('%', UPPER(:address), '%')) 
            AND (:salonTypeId is NULL OR s.salonType.id = :salonTypeId)
            """)
    Salon findByNameAndAddressAndSalonType(@Param("name") String name,
                                           @Param("address") String address,
                                           @Param("salonTypeId") Long salonTypeId);
}
