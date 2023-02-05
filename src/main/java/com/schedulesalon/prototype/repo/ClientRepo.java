package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
}
