package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;

public interface PersonService {
    Person save(Person person) throws Exception;
    void addRoles(Person person, Role[] roles) throws Exception;
    Person find(Person person) throws Exception;
}
