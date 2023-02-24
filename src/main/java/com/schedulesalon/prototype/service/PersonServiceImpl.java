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
        if (roleFinded == null)
            UtilException.throwDefault(UtilException.ROLE_NOT_FOUND);

        Person personFinded = find(person);
        if (personFinded == null)
            UtilException.throwDefault(UtilException.USER_NOT_FOUND);

        person.getRoles().add(role);
        personRepo.save(person);
    }

    @Override
    public Person find(Person person) throws Exception {
        String[] params = { person.getName(), person.getPhoneNumber(), person.getEmail()};
        UtilParam.throwExceptionIfStringParamsAreNotFilled(params);
        return personRepo.findByNameAndPhoneNumberAndEmail(person.getName(), person.getPhoneNumber(), person.getEmail());
    }
}
