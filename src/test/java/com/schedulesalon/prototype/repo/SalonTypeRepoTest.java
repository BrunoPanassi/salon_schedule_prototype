package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Job;
import com.schedulesalon.prototype.model.SalonType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class SalonTypeRepoTest {

    @Autowired
    SalonTypeRepo salonTypeRepo;

    @Autowired
    JobRepo jobRepo;

    @AfterEach
    void tearDown() {
        salonTypeRepo.deleteAll();
    }

    @Test
    void itShouldFindSalonTypeByDescription() {
        //given
        String certainDescription = "BARBEARIA";
        SalonType salonType = new SalonType(certainDescription);
        salonTypeRepo.save(salonType);

        //when
        SalonType salonTypeFounded = salonTypeRepo.findSalonTypeByDescription(certainDescription);

        //then
        assertThat(salonTypeFounded).isEqualTo(salonType);
    }

    @Test
    void itShouldNotFindSalonTypeByDescription() {
        //given
        String certainDescription = "BARBEARIA";
        String wrongDescription = "BARBEARIAS";
        SalonType salonType = new SalonType(certainDescription);
        salonTypeRepo.save(salonType);

        //when
        SalonType salonTypeFounded = salonTypeRepo.findSalonTypeByDescription(wrongDescription);

        //then
        assertThat(salonTypeFounded).isNull();
    }

    @Test
    void itShouldFindSalonTypeByJobDescription() {
        //given
        Job cabeleleiro = new Job("CABELELEIRO");
        Job barbeiro = new Job("BARBEIRO");

        jobRepo.save(cabeleleiro);
        jobRepo.save(barbeiro);

        Job[] jobs = {cabeleleiro, barbeiro};
        SalonType barbearia = new SalonType("BARBEARIA");
        barbearia.setJob(List.of(jobs));

        salonTypeRepo.save(barbearia);

        //when
        SalonType salonTypeFounded = salonTypeRepo.findSalonTypeByJobDescription(cabeleleiro.getDescription());

        //then
        assertThat(salonTypeFounded).isEqualTo(barbearia);
    }

    @Test
    void itShoulNotdFindSalonTypeByJobDescription() {
        //given
        String nonExistentDescription = "MAQUEADOR";
        Job cabeleleiro = new Job("CABELELEIRO");
        Job barbeiro = new Job("BARBEIRO");

        jobRepo.save(cabeleleiro);
        jobRepo.save(barbeiro);

        Job[] jobs = {cabeleleiro, barbeiro};
        SalonType barbearia = new SalonType("BARBEARIA");
        barbearia.setJob(List.of(jobs));

        salonTypeRepo.save(barbearia);

        //when
        SalonType salonTypeFounded = salonTypeRepo.findSalonTypeByJobDescription(nonExistentDescription);

        //then
        assertThat(salonTypeFounded).isNull();
    }
}