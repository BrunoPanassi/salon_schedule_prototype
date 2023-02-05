package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByNameAndPhoneNumberAndEmail(String name, String phoneNumber, String email);
}
