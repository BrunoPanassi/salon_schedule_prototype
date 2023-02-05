package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Manager;
import com.schedulesalon.prototype.repo.ManagerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class ManagerServiceImpl implements ManagerService{
    private final ManagerRepo managerRepo;
    @Override
    public Manager saveManager(Manager manager) {
        //TODO: Check if exists another user with this name;
        return managerRepo.save(manager);
    }

    @Override
    public List<Manager> getManagers() {
        return managerRepo.findAll();
    }
}
