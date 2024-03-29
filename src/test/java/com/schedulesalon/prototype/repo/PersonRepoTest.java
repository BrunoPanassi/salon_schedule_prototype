package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepoTest {

    @Autowired
    PersonRepo personRepo;

    @AfterEach
    void tearDown() {
        personRepo.deleteAll();
    }

    @Test
    void itShouldFindByNameAndPhoneNumberAndEmail() {
        //given
        String name = "Michael Jackson";
        String password = "billiejean";
        String email = "michael@hotmail.com";
        String phoneNumber = "18 997 555";

        Person michael = new Person(
                name,
                password,
                phoneNumber,
                email
        );

        personRepo.save(michael);

        //when
        Person personFinded = personRepo.findByNameAndPhoneNumberAndEmail(name, phoneNumber, email);

        //then
        assertThat(personFinded).isEqualTo(michael);
    }

    @Test
    void itShouldNotFindByNameAndPhoneNumberAndEmail() {
        //given
        String name = "Michael Jackson";
        String password = "billiejean";
        String email = "michael@hotmail.com";
        String phoneNumber = "18 997 555";

        String wrongName = "MIkael Jackson";

        Person michael = new Person(
                name,
                password,
                phoneNumber,
                email
        );

        personRepo.save(michael);

        //when
        Person personFinded = personRepo.findByNameAndPhoneNumberAndEmail(wrongName, phoneNumber, email);

        //then
        assertThat(personFinded).isNull();
    }
}