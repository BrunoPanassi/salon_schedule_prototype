package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.*;
import com.schedulesalon.prototype.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepo roleRepo;
    private final ClientRepo clientRepo;
    private final ManagerRepo managerRepo;
    private final ProfessionalRepo professionalRepo;

    @Override
    public Role saveRole(Role role) throws Exception {
        String type = role.getType();
        Role findedRole = roleRepo.findByType(type);
        if (findedRole != null) {
            throw new Exception("Esta Role já existe");
        }
        return roleRepo.save(role);
    }

    @Override
    public Role findRole(String type) {
        return roleRepo.findByType(type);
    }

    public void eachRoleToEachOwnTable(Person person, Role.TypeRole role) {
        Role.TypeRole typeRole;
        switch (role) {
            case CLIENT:
                Client client = new Client(person);
                //TO DO: Each save change to implement override in service ckecking if data exists
                clientRepo.save(client);
                break;
            case PROFESSIONAL:
                Professional professional = new Professional(person);
                professionalRepo.save(professional);
                break;
            case MANAGER:
                Manager manager = new Manager(person);
                managerRepo.save(manager);
                break;
        }
    }
}
