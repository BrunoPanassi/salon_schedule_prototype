package com.schedulesalon.prototype.repo;

import com.schedulesalon.prototype.model.Job;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class JobRepoTest {

    @Autowired
    JobRepo jobRepo;

    @AfterEach
    void tearDown() {
        jobRepo.deleteAll();
    }

    @Test
    void itShouldfindJobsByDescription() {
        //given
        Job maqueador = new Job("Maqueador");
        jobRepo.save(maqueador);

        //when
        List<Job> findedJobs = jobRepo.findJobsByDescription("MAQUEADOR");

        //then
        assertThat(findedJobs.get(0)).isEqualTo(maqueador);
    }

    @Test
    void itNotShoulFindJobsByDescription() {
        //given
        Job maqueador = new Job("Maqueador");
        jobRepo.save(maqueador);

        //when
        List<Job> findedJobs = jobRepo.findJobsByDescription("MAQUEADORES");

        //then
        assertEquals(0, findedJobs.size());
    }

    @Test
    void itShouldfindAListOfJobsByDescription() {
        //given
        Job maqueador = new Job("Maqueador");
        Job maqueadora = new Job("Maqueadora");
        Job[] jobs = {maqueador, maqueadora};

        for (Job job : jobs) {
            jobRepo.save(job);
        }

        //when
        List<Job> findedJobs = jobRepo.findJobsByDescription("MAQUEADOR");

        //then
        assertArrayEquals(jobs, findedJobs.toArray());
    }
}