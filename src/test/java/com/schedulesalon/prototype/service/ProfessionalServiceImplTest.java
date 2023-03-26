package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Job;
import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Professional;
import com.schedulesalon.prototype.repo.ProfessionalRepo;
import com.schedulesalon.prototype.util.UtilException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProfessionalServiceImplTest {

    @Mock
    private ProfessionalRepo professionalRepo;

    private ProfessionalServiceImpl professionalService;

    @BeforeEach
    void setUp() {
        professionalService = new ProfessionalServiceImpl(professionalRepo);
    }

    @Test
    void itShouldSaveProfessional() throws Exception {
        //given
        Person person = new Person(
                "Dwight Schrute",
                "dwight",
                "18 997 444",
                "dwight@dundermifflin.com"
        );
        Professional professional = new Professional(person);
        professionalService.save(professional);

        //when
        ArgumentCaptor<Professional> professionalArgumentCaptor = ArgumentCaptor.forClass(Professional.class);
        verify(professionalRepo).save(professionalArgumentCaptor.capture());
        Professional capturedProfessional = professionalArgumentCaptor.getValue();

        //then
        assertThat(capturedProfessional).isEqualTo(professional);
    }

    @Test
    void itNotShouldSaveProfessionalBecauseAlreadyExists() throws Exception {
        //given
        Person person = new Person(
                "Dwight Schrute",
                "dwight",
                "18 997 444",
                "dwight@dundermifflin.com"
        );
        Professional professional = new Professional(person);
        String[] exceptionMessageParams = {Professional.objectName};

        given(professionalService.find(person)).willReturn(professional);

        //when
        assertThatThrownBy(() -> professionalService.save(professional))
                .isInstanceOf(Exception.class)
                        .hasMessageContaining(UtilException.ExceptionBuilder(
                                UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA_WITH_PARAM,
                                exceptionMessageParams
                        ));

        //then
        verify(professionalRepo, never()).save(any());
    }

    @Test
    void itNotShouldSaveProfessionalBecauseParamsIsNull() throws Exception {
        //given
        //when
        assertThatThrownBy(() -> professionalService.save(null))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.ALL_PARAMS_ARE_NOT_FILLED);

        //then
        verify(professionalRepo, never()).save(any());
    }

    @Test
    @Disabled
    void itShouldRegisterWhichJobsTheProfessionalHave() throws Exception {
        //given
        Person person = new Person(
                "Dwight Schrute",
                "dwight",
                "18 997 444",
                "dwight@dundermifflin.com"
        );
        Professional professional = new Professional(person);

        Job cabeleleiro = new Job("Cabeleleiro");
        Job barbeiro = new Job("Barbeiro");
        Job[] jobs = {cabeleleiro, barbeiro};

        //when
        professional.setJobs(List.of(jobs));
        professionalService.save(professional);

        //then
        ArgumentCaptor<Professional> professionalArgumentCaptor = ArgumentCaptor.forClass(Professional.class);
        verify(professionalRepo).save(professionalArgumentCaptor.capture());
        Professional capturedProfessional = professionalArgumentCaptor.getValue();

        assertArrayEquals(capturedProfessional.getJobs().toArray(), jobs);
    }

    @Test
    @Disabled
    void itShouldRegisterInASalon() {

    }

    @Test
    @Disabled
    void itShouldRegisterTheAvailableHours() {

    }
}