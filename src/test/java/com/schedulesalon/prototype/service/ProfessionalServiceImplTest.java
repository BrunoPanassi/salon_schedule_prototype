package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.hour.AvailableDate;
import com.schedulesalon.prototype.model.*;
import com.schedulesalon.prototype.repo.*;
import com.schedulesalon.prototype.util.UtilDate;
import com.schedulesalon.prototype.util.UtilException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProfessionalServiceImplTest {

    @Mock
    private ProfessionalRepo professionalRepo;
    @Mock
    private SalonRepo salonRepo;
    @Mock
    private PersonRepo personRepo;
    @Mock
    private RoleRepo roleRepo;
    @Mock
    private ManagerRepo managerRepo;

    private PersonServiceImpl personService;
    private RoleServiceImpl roleService;
    private ManagerServiceImpl managerService;
    private ProfessionalServiceImpl professionalService;
    private ClientServiceImpl clientService;
    private SalonServiceImpl salonService;

    @BeforeEach
    void setUp() {
        roleService = new RoleServiceImpl(roleRepo, managerService, clientService, professionalService);
        personService = new PersonServiceImpl(roleRepo, personRepo, roleService);
        managerService = new ManagerServiceImpl(managerRepo);
        professionalService = new ProfessionalServiceImpl(professionalRepo);
        salonService = new SalonServiceImpl(salonRepo);
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
    void itShouldRegisterInASalon() throws Exception {
        //given
        Person personToBeTheProfessional = new Person(
                "Dwight Schrute",
                "dwight",
                "18 997 444",
                "dwight@dundermifflin.com"
        );
        Person personToBeTheManager = new Person(
                "Michael Scott",
                "password123",
                "18 997 555",
                "michael@dundermifflin.com"
        );
        Professional professional = new Professional(personToBeTheProfessional);
        Manager manager = new Manager(personToBeTheManager);
        SalonType barbearia = new SalonType("Barbearia");

        Salon salon = new Salon(
                "King Barbearia",
                "Barbearia Old School",
                "Rua Ficticia, N 175, Bairro Ipan",
                barbearia,
                manager
        );
        salon = salonService.addProfessional(professional, salon);

        //when
        personService.save(personToBeTheProfessional);
        personService.save(personToBeTheManager);
        professionalService.save(professional);
        managerService.save(manager);
        salonService.saveOrUpdate(salon);

        //then
        ArgumentCaptor<Salon> salonArgumentCaptor = ArgumentCaptor.forClass(Salon.class);
        verify(salonRepo).save(salonArgumentCaptor.capture());
        Salon salonFinded = salonArgumentCaptor.getValue();

        assertThat(salonFinded.getProfessionals()).isEqualTo(salon.getProfessionals());

    }

    @Test
    @Disabled
    void itShouldRegisterTheSameProfessionalAsTheManagerOfTheSalon() throws Exception {
        //given
        Person personToBeTheProfessionalAndAlsoTheManager = new Person(
                "Michael Scott",
                "password123",
                "18 997 555",
                "michael@dundermifflin.com"
        );
        Professional professional = new Professional(personToBeTheProfessionalAndAlsoTheManager);
        Manager manager = new Manager(personToBeTheProfessionalAndAlsoTheManager);
        SalonType barbearia = new SalonType("Barbearia");

        Salon salon = new Salon(
                "King Barbearia",
                "Barbearia Old School",
                "Rua Ficticia, N 175, Bairro Ipan",
                barbearia,
                manager
        );
        salon = salonService.addProfessional(professional, salon);

        //when
        personService.save(personToBeTheProfessionalAndAlsoTheManager);
        professionalService.save(professional);
        managerService.save(manager);
        salonService.saveOrUpdate(salon);

        //then
        ArgumentCaptor<Salon> salonArgumentCaptor = ArgumentCaptor.forClass(Salon.class);
        verify(salonRepo).save(salonArgumentCaptor.capture());
        Salon salonFinded = salonArgumentCaptor.getValue();

        final Salon finalSalon = salon;
        assertTrue(salonFinded.getProfessionals().stream().anyMatch((prof) -> prof.getPerson().equals(finalSalon.getManager().getPerson())));
    }

    @Test
    @Disabled
    void itShouldThrowExceptionBecauseToAddTheProfessionalMustHaveAManagerInSalon() {

    }

    @Test
    void itShouldRegisterTheAvailableHours() throws Exception {
        //given
        int year = UtilDate.actualDate.getYear();
        int month = UtilDate.actualDate.getMonthValue();
        int tomorrow = UtilDate.actualDate.getDayOfMonth() + 1;

        AvailableDate availableDate = new AvailableDate(
                tomorrow,
                month,
                year,
                8,
                12,
                13,
                18,
                0,
                0,
                0
        );
        Person personToBeTheProfessional = new Person(
                "Dwight Schrute",
                "dwight",
                "18 997 444",
                "dwight@dundermifflin.com"
        );
        Professional professional = new Professional(personToBeTheProfessional);
        List<Hour> hoursToBeChecked = List.of(new Hour[]{
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 8, 0),
                        LocalDateTime.of(year, month, tomorrow, 9, 0),
                        professional
                ),
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 9, 0),
                        LocalDateTime.of(year, month, tomorrow, 10, 0),
                        professional
                ),
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 10, 0),
                        LocalDateTime.of(year, month, tomorrow, 11, 0),
                        professional
                ),
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 11, 0),
                        LocalDateTime.of(year, month, tomorrow, 12, 0),
                        professional
                ),
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 13, 0),
                        LocalDateTime.of(year, month, tomorrow, 14, 0),
                        professional
                ),
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 14, 0),
                        LocalDateTime.of(year, month, tomorrow, 15, 0),
                        professional
                ),
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 15, 0),
                        LocalDateTime.of(year, month, tomorrow, 16, 0),
                        professional
                ),
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 16, 0),
                        LocalDateTime.of(year, month, tomorrow, 17, 0),
                        professional
                ),
                new Hour(
                        LocalDateTime.of(year, month, tomorrow, 17, 0),
                        LocalDateTime.of(year, month, tomorrow, 18, 0),
                        professional
                )
        });

        //when
        professional = professionalService.addRangeOfAvailableHoursToSpecificDay(professional, availableDate);

        //then
        assertArrayEquals(professional.getHours().toArray(), hoursToBeChecked.toArray());
    }

    //TODO: All this tests below
    /*
    verifyActualDate
    verifyBreakHour
    verifyWhichAvailableDatePropsCantBeNullOrEmpty
    initialHourAndFinalCannottBeEqualToEachOtherEitherZeros
    hourCreatedDontAlreadyExistsWithTheProfessional
     */

    @Test
    @Disabled
    void itShouldUpdateTheJobsHisHave() {

    }
}