package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Role;
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
    void itShouldSaveClientTable() {
    }

    @Test
    void itShouldSaveProfessionalTable() {
    }

    @Test
    void itShouldSaveManagerTable() {
    }
}