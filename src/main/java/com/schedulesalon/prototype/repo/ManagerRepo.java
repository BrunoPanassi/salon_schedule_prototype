package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepo extends JpaRepository<Manager, Long> {
}
