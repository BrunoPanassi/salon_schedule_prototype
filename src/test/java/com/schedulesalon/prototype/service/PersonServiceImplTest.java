package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;
import com.schedulesalon.prototype.repo.PersonRepo;
import com.schedulesalon.prototype.repo.RoleRepo;
import com.schedulesalon.prototype.util.UtilException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepo personRepo;
    @Mock
    private RoleRepo roleRepo;
    private PersonServiceImpl personService;
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl(roleRepo, personRepo);
        roleService = new RoleServiceImpl(roleRepo, personRepo);
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
    void shouldThrowErrorWhenTryToSavePerson() throws Exception {
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
    @Disabled
    void shouldAddManagerRole() throws Exception {
        // given
        Role.TypeRole[] rolesToAdd = {Role.TypeRole.MANAGER};

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

        assertArrayEquals(capturedPerson.getRoles().toArray(), roles);
    }

    @Test
    void shouldAddManagerAndProfessionalRoles() throws Exception {
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

        assertArrayEquals(capturedPerson.getRoles().toArray(), roles);
    }

    @Test
    void shouldNotAddClientAmongOtherRoles() throws Exception {
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
    @Disabled
    void shouldAddRoleProfessionalAndCreateDataOnTableProfessional() throws Exception {

    }

    @Test
    @Disabled
    void shouldAddRoleManagerAndCreateDataOnTableManager() throws Exception {

    }

    @Test
    @Disabled
    void shouldAddRoleClientAndCreateDataOnTableClient() throws Exception {

    }
}