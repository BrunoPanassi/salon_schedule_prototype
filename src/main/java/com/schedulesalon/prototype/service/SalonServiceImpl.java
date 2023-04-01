package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Manager;
import com.schedulesalon.prototype.model.Professional;
import com.schedulesalon.prototype.model.Salon;
import com.schedulesalon.prototype.model.SalonType;
import com.schedulesalon.prototype.repo.SalonRepo;
import com.schedulesalon.prototype.util.UtilException;
import com.schedulesalon.prototype.util.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class SalonServiceImpl implements SalonService{
    private final SalonRepo salonRepo;
    @Override
    public Salon save(Salon salon) throws Exception {
        UtilParam.throwExceptionIfObjectParamIsNull(salon);
        Salon salonFinded = find(salon.getName(), salon.getAddress(), salon.getSalonType());
        throwExceptionIfSalonAlreadyExists(salonFinded);
        return salonRepo.save(salon);
    }

    private void throwExceptionIfSalonAlreadyExists(Salon salonFinded) throws Exception {
        if (salonFinded != null) {
            String[] exceptionMessageParams = {Salon.objectName};
            UtilException.throwDefault(
                    UtilException.ExceptionBuilder(
                            UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA_WITH_PARAM,
                            exceptionMessageParams
                    )
            );
        }
    }

    @Override
    public Salon find(String name, String address, SalonType salonType) throws Exception {
        return salonRepo.findByNameAndAddressAndSalonType(name, address, salonType.getId());
    }

    @Override
    public Salon update(Salon salon) throws Exception {
        UtilParam.throwExceptionIfObjectParamIsNull(salon);
        return salonRepo.save(salon);
    }

    @Override
    public Salon delete(Salon salon) throws Exception {
        return null;
    }

    @Override
    public Salon saveOrUpdate(Salon salon) throws Exception {
        if (salon.getId() == null) {
            return save(salon);
        }
        return update(salon);
    }

    @Override
    public Salon addProfessional(Professional professional, Salon salon) throws Exception {
        checkIfSalonAlreadyHasAManager(salon);
        checkIfSalonAlreadyHaveTheProfessional(professional, salon);
        if (salon.getProfessionals() == null) {
            Professional[] professionals = {professional};
            salon.setProfessionals(List.of(professionals));
        } else {
            salon.getProfessionals().add(professional);
        }
        return salon;
    }

    private void checkIfSalonAlreadyHasAManager(Salon salon) throws Exception {
        if (salon.getManager() == null)
            UtilException.throwDefault(UtilException.CANT_INSERT_A_PROFESSIONAL_SALON_DOESNT_HAVE_A_MANAGER);
    }

    private void checkIfSalonAlreadyHaveTheProfessional(Professional professionalToBeAdd, Salon salon) throws Exception {
        if (doesThisSalonHaveThisProfessional(professionalToBeAdd, salon)) {
            String[] exceptionMessageParams = {Professional.objectName};
            UtilException.throwDefault(UtilException.ExceptionBuilder(
                    UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA_WITH_PARAM,
                    exceptionMessageParams
            ));
        }
    }

    private Boolean doesThisSalonHaveThisProfessional(Professional professionalToBeCheck, Salon salon) {
        if (salon.getProfessionals() != null)
            return salon.getProfessionals().stream().anyMatch(professional -> professional.equals(professionalToBeCheck));
        return false;
    }

    public Salon addManager(Manager manager, Salon salon) throws Exception {
        checkIfSalonAlreadyHasTheManager(manager, salon);
        salon.setManager(manager);
        return salon;
    }

    private void checkIfSalonAlreadyHasTheManager(Manager manager, Salon salon) throws Exception {
        if (salon.getManager().getPerson().getId() == manager.getPerson().getId())
            UtilException.throwDefault(UtilException.THIS_SALON_ALREADY_HAVE_THIS_MANAGER);
    }
}
