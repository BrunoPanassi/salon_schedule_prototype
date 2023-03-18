package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepo extends JpaRepository<Professional, Long> {
}
