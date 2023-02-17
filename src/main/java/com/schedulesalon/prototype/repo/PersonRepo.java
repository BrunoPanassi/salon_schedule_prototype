package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByNameAndPhoneNumberAndEmail(String name, String phoneNumber, String email);
}
