package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Role;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepoTest {

    @Autowired
    RoleRepo roleRepo;

    @AfterEach
    void tearDown() {
        roleRepo.deleteAll();
    }

    @Test
    void itShouldFindByType() {
        //given
        Role clientRole = new Role(Role.TypeRole.CLIENT);
        roleRepo.save(clientRole);

        //when
        Role roleFinded = roleRepo.findByType(clientRole.getType());

        //then
        AssertionsForClassTypes.assertThat(roleFinded).isEqualTo(clientRole);
    }

    @Test
    void itShouldNotFindByType() {
        //given
        Role professionalRole = new Role(Role.TypeRole.PROFESSIONAL);
        roleRepo.save(professionalRole);

        //when
        Role roleFinded = roleRepo.findByType("PROFESIONAL");

        //then
        AssertionsForClassTypes.assertThat(roleFinded).isNull();
    }
}