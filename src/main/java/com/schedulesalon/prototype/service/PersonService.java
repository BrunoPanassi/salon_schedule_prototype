package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;

public interface PersonService {
    Person save(Person person) throws Exception;
    void addRole(Person person, Role role) throws Exception;
    Person find(Person person) throws Exception;
}
