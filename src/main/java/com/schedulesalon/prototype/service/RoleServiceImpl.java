package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.*;
import com.schedulesalon.prototype.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepo roleRepo;
    private final ManagerService managerService;
    private final ClientService clientService;
    private final ProfessionalService professionalService;

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

    public void eachRoleToEachOwnTable(Person person, Role.TypeRole role) throws Exception {
        Role.TypeRole typeRole;
        switch (role) {
            case CLIENT:
                Client client = new Client(person);
                clientService.save(client);
                break;
            case PROFESSIONAL:
                Professional professional = new Professional(person);
                professionalService.save(professional);
                break;
            case MANAGER:
                Manager manager = new Manager(person);
                managerService.save(manager);
                break;
        }
    }
}
