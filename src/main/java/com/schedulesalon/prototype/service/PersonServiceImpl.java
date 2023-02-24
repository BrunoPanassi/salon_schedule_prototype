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
    public Person save(String name, String phoneNumber, String email, String password) throws Exception {
        Person personFinded = find(name, phoneNumber, email);
        if (personFinded != null) {
            UtilException.throwDefault(UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA);
        }
        return personRepo.save(new Person(name, password, phoneNumber, email));
    }

    @Override
    public void addRole(String personName, String phoneNumber, String email, String roleType) throws Exception {
        Role role = roleRepo.findByType(roleType);
        if (role == null)
            UtilException.throwDefault(UtilException.ROLE_NOT_FOUND);

        Person person = find(personName, phoneNumber, email);

        person.getRoles().add(role);
        personRepo.save(person);
    }

    @Override
    public Person find(String name, String phoneNumber, String email) throws Exception {
        String[] params = { name, phoneNumber, email};
        UtilParam.throwExceptionIfStringParamsAreNotFilled(params);
        return personRepo.findByNameAndPhoneNumberAndEmail(name, phoneNumber, email);
    }
}
