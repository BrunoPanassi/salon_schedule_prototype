package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;
import com.schedulesalon.prototype.repo.PersonRepo;
import com.schedulesalon.prototype.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepo roleRepo;
    private final PersonRepo personRepo;

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
}
