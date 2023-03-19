package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.*;
import com.schedulesalon.prototype.repo.*;
import com.schedulesalon.prototype.util.UtilException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepo personRepo;
    @Mock
    private RoleRepo roleRepo;
    @Mock
    private ClientRepo clientRepo;
    @Mock
    private ManagerRepo managerRepo;
    @Mock
    private ProfessionalRepo professionalRepo;
    private PersonServiceImpl personService;
    private RoleServiceImpl roleService;

    private ManagerService managerService;
    private ClientService clientService;
    private ProfessionalService professionalService;

    @BeforeEach
    void setUp() {
        managerService = new ManagerServiceImpl(managerRepo);
        clientService = new ClientServiceImpl(clientRepo);
        professionalService = new ProfessionalServiceImpl(professionalRepo);
        roleService = new RoleServiceImpl(roleRepo, managerService, clientService, professionalService);
        personService = new PersonServiceImpl(roleRepo, personRepo, roleService);
    }

    @Test
    void shouldSavePerson() throws Exception {
        // given
        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        // when
        personService.save(michael);

        //then
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);

        verify(personRepo).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        assertThat(capturedPerson).isEqualTo(michael);
    }

    @Test
    void shouldNotSavePersonBecauseAlreadyHasAPersonWithThatData() throws Exception {
        // given
        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        given(personService.find(michael)).willReturn(michael);

        //when
        //then
        assertThatThrownBy(() -> personService.save(michael))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA);

        verify(personRepo, never()).save(any());
    }

    @Test
    void shouldAddClientRoleAndSaveClientTable() throws Exception {
        // given
        Role.TypeRole[] rolesToAdd = {Role.TypeRole.CLIENT};

        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Role[] roles = personService.typeRolesToRoles(rolesToAdd);

        given(personService.find(michael)).willReturn(michael);
        Arrays.stream(roles).forEach(role -> {
            given(roleRepo.findByType(role.getType())).willReturn(role);
        });

        // when
        personService.addRoles(michael, roles);

        // then
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepo).save(personArgumentCaptor.capture());
        Person capturedPerson = personArgumentCaptor.getValue();

        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepo).save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();

        assertArrayEquals(capturedPerson.getRoles().toArray(), roles);
        assertThat(capturedClient.getPerson()).isEqualTo(michael);
    }

    @Test
    void shouldNotAddManagerRoleAndNotSaveManagerTableBecauseAlreadyExistsAManagerRole() throws Exception {
        // given
        Role.TypeRole[] rolesToAdd = {Role.TypeRole.MANAGER};
        String[] rolesParamsToException = {Role.TypeRole.MANAGER.getTypeRole()};

        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Role[] roles = personService.typeRolesToRoles(rolesToAdd);

        given(personService.find(michael)).willReturn(michael);
        Arrays.stream(roles).forEach(role -> {
            given(roleRepo.findByType(role.getType())).willReturn(role);
        });

        // when
        personService.addRoles(michael, roles);

        // then
        assertThatThrownBy(() -> personService.addRoles(michael, roles))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(
                        UtilException.ExceptionBuilder(UtilException.PERSON_ALREADY_HAS_THIS_ROLE, rolesParamsToException)
                );
    }

    @Test
    void shouldNotAddRoleBecauseUserWasNotFound() throws Exception {
        // given
        Role.TypeRole[] rolesToAdd = {Role.TypeRole.MANAGER};
        String[] rolesParamsToException = {Role.TypeRole.MANAGER.getTypeRole()};

        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Role[] roles = personService.typeRolesToRoles(rolesToAdd);
        given(personService.find(michael)).willReturn(null);

        // when then
        assertThatThrownBy(() -> personService.addRoles(michael, roles))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.USER_NOT_FOUND);
    }

    @Test
    void shouldAddManagerAndProfessionalRolesAndSaveOnEachTable() throws Exception {
        //given
        Role.TypeRole[] rolesToAdd = {
                Role.TypeRole.PROFESSIONAL,
                Role.TypeRole.MANAGER
        };

        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Role[] roles = personService.typeRolesToRoles(rolesToAdd);

        given(personService.find(michael)).willReturn(michael);
        Arrays.stream(roles).forEach(role -> {
            given(roleRepo.findByType(role.getType())).willReturn(role);
        });

        //when
        personService.addRoles(michael, roles);

        //then
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepo).save(personArgumentCaptor.capture());
        Person capturedPerson = personArgumentCaptor.getValue();

        ArgumentCaptor<Professional> professionalArgumentCaptor = ArgumentCaptor.forClass(Professional.class);
        verify(professionalRepo).save(professionalArgumentCaptor.capture());
        Professional capturedProfessional = professionalArgumentCaptor.getValue();

        ArgumentCaptor<Manager> managerArgumentCaptor = ArgumentCaptor.forClass(Manager.class);
        verify(managerRepo).save(managerArgumentCaptor.capture());
        Manager capturedManager = managerArgumentCaptor.getValue();

        assertArrayEquals(capturedPerson.getRoles().toArray(), roles);
        assertThat(capturedProfessional.getPerson()).isEqualTo(michael);
        assertThat(capturedManager.getPerson()).isEqualTo(michael);
    }

    @Test
    void shouldNotAddClientRoleAmongOtherRoles() throws Exception {
        //given
        Role.TypeRole[] rolesToAdd = {
                Role.TypeRole.PROFESSIONAL,
                Role.TypeRole.CLIENT
        };

        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Role[] roles = personService.typeRolesToRoles(rolesToAdd);

        //when
        //then
        assertThatThrownBy(() -> personService.addRoles(michael, roles))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.ROLE_CLIENT_AMONG_OTHER_ROLES);
    }

    @Test
    void shouldThrowExceptionOfRolesWithCountZero() throws Exception {
        // given
        Role[] roles = {};
        String[] rolesParamsToException = {Role.TypeRole.MANAGER.getTypeRole()};

        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        // when then
        assertThatThrownBy(() -> personService.addRoles(michael, roles))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.ROLES_WITH_COUNT_ZERO);
    }

    @Test
    void shouldThrowRoleNotFound() throws Exception {
        // given
        Role.TypeRole[] rolesToAdd = {Role.TypeRole.MANAGER};
        String[] rolesParamsToException = {Role.TypeRole.MANAGER.getTypeRole()};

        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Role[] roles = personService.typeRolesToRoles(rolesToAdd);
        given(personService.find(michael)).willReturn(michael);
        Arrays.stream(roles).forEach(role -> {
            given(roleRepo.findByType(role.getType())).willReturn(null);
        });

        // when then
        assertThatThrownBy(() -> personService.addRoles(michael, roles))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.ROLE_NOT_FOUND);
    }
}