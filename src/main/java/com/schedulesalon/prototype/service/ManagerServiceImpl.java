package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Manager;
import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.repo.ManagerRepo;
import com.schedulesalon.prototype.util.UtilException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class ManagerServiceImpl implements ManagerService{
    private final ManagerRepo managerRepo;
    @Override
    public Manager save(Manager manager) throws Exception {
        Manager managerFinded = find(manager.getPerson());
        if (managerFinded != null) {
            UtilException.throwDefault(UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA);
        }
        return managerRepo.save(manager);
    }

    @Override
    public Manager find(Person person) {
        Long personId = person.getId();
        return managerRepo.findManagerByPersonId(personId);
    }


    @Override
    public List<Manager> getManagers() {
        return managerRepo.findAll();
    }
}
