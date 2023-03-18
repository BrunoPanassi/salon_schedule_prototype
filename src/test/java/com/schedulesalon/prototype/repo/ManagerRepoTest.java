package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Manager;
import com.schedulesalon.prototype.model.Person;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ManagerRepoTest {
    @Autowired
    ManagerRepo managerRepo;

    @Autowired
    PersonRepo personRepo;

    @AfterEach
    void tearDown() {
        managerRepo.deleteAll();
        personRepo.deleteAll();
    }

    @Test
    void itShouldFindManagerByPersonId() {
        //given
        Person michael = new Person(
                "Michael Scott",
                "password123",
                "18 997 666",
                "michael@dundermiflin.com"
        );

        Person personSaved = personRepo.save(michael);
        Manager manager = new Manager(personSaved);
        managerRepo.save(manager);

        //when
        Manager managerFinded = managerRepo.findManagerByPersonId(manager.getPerson().getId());

        //then
        assertThat(managerFinded).isEqualTo(manager);
    }

    @Test
    void itShouldNotFindManagerByPersonId() {
        //given
        Person michael = new Person(
                "Michael Scott",
                "password123",
                "18 997 666",
                "michael@dundermiflin.com"
        );

        Person personSaved = personRepo.save(michael);

        //when
        Manager managerFinded = managerRepo.findManagerByPersonId(personSaved.getId());

        //then
        assertThat(managerFinded).isNull();
    }
}