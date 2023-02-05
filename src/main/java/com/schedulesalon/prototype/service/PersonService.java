package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;

public interface PersonService {
    Person savePerson(String name, String phoneNumber, String email, String password) throws Exception;
    void addRoleToPerson(String username, String phoneNumber, String email, String roleType);
    Person search(String name, String phoneNumber, String email) throws Exception;
}