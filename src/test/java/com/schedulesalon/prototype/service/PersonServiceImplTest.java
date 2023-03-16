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
    void shouldAddOneSingleRole() throws Exception {
        // given
        Role.TypeRole[] rolesToAdd = {Role.TypeRole.MANAGER};
        String stringRole = rolesToAdd[0].getTypeRole();
        Role role = new Role(rolesToAdd[0]);
        Role[] personRoles = {role};

        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        given(personService.find(michael)).willReturn(michael);
        given(roleRepo.findByType(stringRole)).willReturn(role);

        // when
        personService.addRole(michael, role);

        // then
        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personRepo).save(personArgumentCaptor.capture());
        Person capturedPerson = personArgumentCaptor.getValue();

        assertArrayEquals(capturedPerson.getRoles().toArray(), personRoles);
    }

    @Test
    void shouldAddMultiRoles() throws Exception {
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

        Role[] roles = Arrays
                .stream(rolesToAdd)
                .map(typeRole -> new Role(typeRole))
                .toArray(Role[]::new);

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
}