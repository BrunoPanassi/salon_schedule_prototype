package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.*;
import com.schedulesalon.prototype.repo.ClientRepo;
import com.schedulesalon.prototype.repo.ManagerRepo;
import com.schedulesalon.prototype.repo.ProfessionalRepo;
import com.schedulesalon.prototype.repo.RoleRepo;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import com.schedulesalon.prototype.util.UtilException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private ClientRepo clientRepo;
    @Mock
    private ManagerRepo managerRepo;
    @Mock
    private ProfessionalRepo professionalRepo;
    @Mock
    private RoleRepo roleRepo;
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
    }

    @Test
    void itShouldSaveRoleProfessional() throws Exception {
        //given
        Role professional = new Role(Role.TypeRole.PROFESSIONAL);

        //when
        roleService.save(professional);

        //then
        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepo).save(roleArgumentCaptor.capture());
        Role caputredRole = roleArgumentCaptor.getValue();
        assertThat(caputredRole).isEqualTo(professional);
    }

    @Test
    void itNotShouldSaveRoleBecauseAlreadyExists() throws Exception {
        //given
        Role professional = new Role(Role.TypeRole.PROFESSIONAL);
        String[] exceptionParams = {Role.objectName};

        given(roleRepo.findByType(professional.getType())).willReturn(professional);

        //when
        //then
        assertThatThrownBy(() -> roleService.save(professional))
                .isInstanceOf(Exception.class)
                .hasMessageContaining(UtilException.ExceptionBuilder(UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA_WITH_PARAM, exceptionParams));

        verify(roleRepo, never()).save(any());
    }

    @Test
    void itShouldSaveClientTable() throws Exception {
        // given
        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Client client = new Client(michael);

        Role.TypeRole clientTypeRole = Role.TypeRole.CLIENT;

        //when
        roleService.eachRoleToEachOwnTable(michael, clientTypeRole);

        //then
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepo).save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();
        assertThat(capturedClient).isEqualTo(client);
    }

    @Test
    void itShouldSaveProfessionalTable() throws Exception {
        // given
        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Professional professional = new Professional(michael);

        Role.TypeRole clientTypeRole = Role.TypeRole.PROFESSIONAL;

        //when
        roleService.eachRoleToEachOwnTable(michael, clientTypeRole);

        //then
        ArgumentCaptor<Professional> professionalArgumentCaptor = ArgumentCaptor.forClass(Professional.class);
        verify(professionalRepo).save(professionalArgumentCaptor.capture());
        Professional capturedProfessional = professionalArgumentCaptor.getValue();
        assertThat(capturedProfessional).isEqualTo(professional);
    }

    @Test
    void itShouldSaveManagerTable() throws Exception {
        // given
        Person michael = new Person(
                "Michael Jackson",
                "billiejean",
                "18 997 555",
                "michael@hotmail.com"
        );

        Manager manager = new Manager(michael);

        Role.TypeRole clientTypeRole = Role.TypeRole.MANAGER;

        //when
        roleService.eachRoleToEachOwnTable(michael, clientTypeRole);

        //then
        ArgumentCaptor<Manager> managerArgumentCaptor = ArgumentCaptor.forClass( Manager.class);
        verify(managerRepo).save(managerArgumentCaptor.capture());
        Manager capturedManager = managerArgumentCaptor.getValue();
        assertThat(capturedManager).isEqualTo(manager);
    }
}