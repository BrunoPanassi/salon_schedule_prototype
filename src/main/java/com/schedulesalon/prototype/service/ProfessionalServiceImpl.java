package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.hour.AvailableDate;
import com.schedulesalon.prototype.model.Client;
import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Professional;
import com.schedulesalon.prototype.repo.ProfessionalRepo;
import com.schedulesalon.prototype.util.UtilAvailableHour;
import com.schedulesalon.prototype.util.UtilException;
import com.schedulesalon.prototype.util.UtilParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service @RequiredArgsConstructor @Transactional
public class ProfessionalServiceImpl implements ProfessionalService{

    private final ProfessionalRepo professionalRepo;
    @Override
    public Professional save(Professional professional) throws Exception {
        UtilParam.throwExceptionIfObjectParamIsNull(professional);
        Professional professionalFinded = find(professional.getPerson());
        if (professionalFinded != null) {
            String[] exceptionMessageParam = {Professional.objectName};
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

    public Professional addMultipleAvailableHours(Professional professional, LocalDateTime[] hours) {
        return professional;
    }

    public Professional addAvailableHour(Professional professional, LocalDateTime hour) {
        return professional;
    }

    private void checkIfProfessionalAlreadyHaveThisHour(Professional professional, LocalDateTime hour) {

    }

    private void checkIfHourIsBiggerThanActualDate(LocalDateTime hour) {

    }

    public Professional addRangeOfAvailableHoursToSpecificDay(Professional professional, AvailableDate availableDate) throws Exception {
        professional = UtilAvailableHour.getFirstAndSecondRoundAndBreakHourToCreateTheRoundsHours(professional, availableDate);
        return professional;
    }
}
