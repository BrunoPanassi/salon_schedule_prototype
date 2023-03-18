package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Manager;
import com.schedulesalon.prototype.model.Person;

import java.util.List;

public interface ManagerService {
    Manager saveManager(Manager manager) throws Exception;
    Manager find(Person person);
    List<Manager> getManagers();
}
