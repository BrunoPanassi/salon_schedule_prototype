package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Client;
import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;
import com.schedulesalon.prototype.repo.PersonRepo;
import com.schedulesalon.prototype.repo.RoleRepo;
import com.schedulesalon.prototype.util.UtilException;
import com.schedulesalon.prototype.util.UtilParam;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class PersonServiceImpl implements PersonService{

    private final RoleRepo roleRepo;
    private final PersonRepo personRepo;
    private final RoleServiceImpl roleService;

    @Override
    public Person save(Person person) throws Exception {
        Person personFinded = find(person);
        if (personFinded != null) {
            UtilException.throwDefault(UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA);
        }
        return personRepo.save(person);
    }

    @Override
    public void addRoles(Person person, Role[] roles) throws Exception {
        verifyRoles(roles);
        Person personFinded = verifyPerson(person);
        Arrays.stream(roles).forEach(role -> {
            try {
                checkIfRoleExists(role);
                //TO DO: Verify if person has already that role
                personFinded.getRoles().add(role);
                roleService.eachRoleToEachOwnTable(personFinded, Role.TypeRole.valueOf(role.getType()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        personRepo.save(personFinded);
    }

    @Override
    public Person find(Person person) throws Exception {
        String[] params = { person.getName(), person.getPhoneNumber(), person.getEmail()};
        UtilParam.throwExceptionIfStringParamsAreNotFilled(params);
        return personRepo.findByNameAndPhoneNumberAndEmail(person.getName(), person.getPhoneNumber(), person.getEmail());
    }

    private Person verifyPerson(Person person) throws Exception {
        Person personFinded = find(person);
        if (personFinded == null)
            UtilException.throwDefault(UtilException.USER_NOT_FOUND);
        return personFinded;
    }

    private void verifyRoles(Role[] roles) throws Exception {
        if(roles.length == 0) {
            UtilException.throwDefault(UtilException.ROLES_WITH_COUNT_ZERO);
        }
        if(isClientRoleAmongOtherRoles(roles)) {
            UtilException.throwDefault(UtilException.ROLE_CLIENT_AMONG_OTHER_ROLES);
        }
    }

    private Boolean isClientRoleAmongOtherRoles(Role[] roles) {
        return roles.length > 1 &&
            Arrays.stream(roles).anyMatch(role -> role.getType() == Role.TypeRole.CLIENT.getTypeRole());
    }

    private void checkIfRoleExists(Role role) throws Exception {
        Role roleFinded = roleRepo.findByType(role.getType());
        if (roleFinded == null)
            UtilException.throwDefault(UtilException.ROLE_NOT_FOUND);
    }

    public Role[] typeRolesToRoles(Role.TypeRole[] typeRoles) {
         return Arrays
                .stream(typeRoles)
                .map(typeRole -> new Role(typeRole))
                .toArray(Role[]::new);
    }
}
