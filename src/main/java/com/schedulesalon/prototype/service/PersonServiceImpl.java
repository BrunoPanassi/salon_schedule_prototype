package com.schedulesalon.prototype.service;

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

    @Override
    public Person save(Person person) throws Exception {
        Person personFinded = find(person);
        if (personFinded != null) {
            UtilException.throwDefault(UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA);
        }
        return personRepo.save(person);
    }

    @Override
    public void addRole(Person person, Role role) throws Exception {
        Role roleFinded = roleRepo.findByType(role.getType());
        checkIfRoleExists(roleFinded);
        Person personFinded = verifyPerson(person);
        personFinded.getRoles().add(role);
        personRepo.save(person);
    }

    private void checkIfRoleExists(Role roleFinded) throws Exception {
        if (roleFinded == null)
            UtilException.throwDefault(UtilException.ROLE_NOT_FOUND);
    }

    @Override
    public void addRoles(Person person, Role[] roles) throws Exception {
        verifyRoles(roles);
        Person personFinded = verifyPerson(person);
        Arrays.stream(roles).forEach(role -> {
            try {
                checkIfRoleExists(role);
                personFinded.getRoles().add(role);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        personRepo.save(personFinded);
    }

    private Person verifyPerson(Person person) throws Exception {
        Person personFinded = find(person);
        if (personFinded == null)
            UtilException.throwDefault(UtilException.USER_NOT_FOUND);
        return personFinded;
    }

    private void verifyRoles(Role[] roles) throws Exception {
        if(Arrays.stream(roles).count() == 0) {
            UtilException.throwDefault(UtilException.ROLES_WITH_COUNT_ZERO);
        }
        if(isClientRoleAmongOtherRoles(roles) == true) {
            UtilException.throwDefault(UtilException.ROLE_CLIENT_AMONG_OTHER_ROLES);
        }
    }

    private Boolean isClientRoleAmongOtherRoles(Role[] roles) {
        if (Arrays.stream(roles).anyMatch(role -> role.getType() == Role.TypeRole.CLIENT.getTypeRole()
            &&
            Arrays.stream(roles).count() > 1)) {
            return true;
        }
        return false;
    }

    @Override
    public Person find(Person person) throws Exception {
        String[] params = { person.getName(), person.getPhoneNumber(), person.getEmail()};
        UtilParam.throwExceptionIfStringParamsAreNotFilled(params);
        return personRepo.findByNameAndPhoneNumberAndEmail(person.getName(), person.getPhoneNumber(), person.getEmail());
    }
}
