package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepo personRepo;
    private RoleRepo roleRepo;
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        personService = new PersonServiceImpl(roleRepo, personRepo);
    }

    @Test
    void canSavePerson() throws Exception {
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
        ArgumentCaptor<Person> personArgumentCaptor =
                ArgumentCaptor.forClass(Person.class);

        verify(personRepo).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        assertThat(capturedPerson).isEqualTo(michael);
    }

    @Test
    void willThrowWhenTryToSave() throws Exception {
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
    }

    @Test
    @Disabled
    void addRole() {
    }

    @Test
    @Disabled
    void find() {
    }
}