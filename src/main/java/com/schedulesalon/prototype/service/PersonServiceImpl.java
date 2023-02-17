package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;
import com.schedulesalon.prototype.repo.PersonRepo;
import com.schedulesalon.prototype.repo.RoleRepo;
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
    public Person savePerson(String name, String phoneNumber, String email, String password) throws Exception {
        String[] params = { name, phoneNumber, email, password};
        if (UtilParam.allStringParamsAreFilled(params)) {
            Person personFinded = personRepo.findByNameAndPhoneNumberAndEmail(name, phoneNumber, email);
            if (personFinded != null) {
                throw new Exception("Já existe um usuário com os mesmos dados!");
            }
            return personRepo.save(new Person(name, password, phoneNumber, email));
        } else {
            throw new Exception("Todos os parâmetros não estão preenchidos!");
        }

    }

    @Override
    public void addRoleToPerson(String personName, String phoneNumber, String email, String roleType) {
        //TO DO: Cada find deveria ter um try catch com uma exceção
        Role role = roleRepo.findByType(roleType);
        Person person = personRepo.findByNameAndPhoneNumberAndEmail(personName, phoneNumber, email);
        person.getRoles().add(role);
        personRepo.save(person);
    }

    @Override
    public Person search(String name, String phoneNumber, String email) throws Exception {
        String[] params = { name, phoneNumber, email};
        if (UtilParam.allStringParamsAreFilled(params)) {
            Person personFinded = personRepo.findByNameAndPhoneNumberAndEmail(name, phoneNumber, email);
            if (personFinded == null) {
                throw new Exception("Usuário não encontrado!");
            }
            return personFinded;
        } else {
            throw new Exception("Todos os parâmetros não estão preenchidos!");
        }
    }
}
