package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Client;
import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Professional;
import com.schedulesalon.prototype.repo.ProfessionalRepo;
import com.schedulesalon.prototype.util.UtilException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class ProfessionalServiceImpl implements ProfessionalService{

    private final ProfessionalRepo professionalRepo;
    @Override
    public Professional save(Professional professional) throws Exception {
        Professional professionalFinded = find(professional.getPerson());
        String[] exceptionMessageParam = {Professional.objectName};
        if (professionalFinded != null) {
            UtilException.throwDefault(
                    UtilException.ExceptionBuilder(
                            UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA_WITH_PARAM,
                            exceptionMessageParam
                    )
            );
        }
        return professionalRepo.save(professional);
    }

    @Override
    public Professional find(Person person) throws Exception {
        Long personId = person.getId();
        return professionalRepo.findProfessionalByPersonId(personId);
    }
}