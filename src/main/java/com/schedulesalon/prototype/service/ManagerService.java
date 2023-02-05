package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Manager;

import java.util.List;

public interface ManagerService {
    Manager saveManager(Manager manager);
    List<Manager> getManagers();
}
