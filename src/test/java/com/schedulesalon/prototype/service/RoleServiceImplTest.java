package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Role;
import com.schedulesalon.prototype.repo.ClientRepo;
import com.schedulesalon.prototype.repo.ManagerRepo;
import com.schedulesalon.prototype.repo.ProfessionalRepo;
import com.schedulesalon.prototype.repo.RoleRepo;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Role roleSaved = roleService.save(professional);

        //then
        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepo).save(roleArgumentCaptor.capture());
        Role caputredRole = roleArgumentCaptor.getValue();
        assertThat(caputredRole).isEqualTo(professional);
    }

    @Test
    void itNotShouldSaveRoleBecauseAlreadyExists() {
    }

    @Test
    void itShouldSaveClientTable() {
    }

    @Test
    void itShouldSaveProfessionalTable() {
    }

    @Test
    void itShouldSaveManagerTable() {
    }
}