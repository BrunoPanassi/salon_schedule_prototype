package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByType(String type);

}
