package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Client;
import com.schedulesalon.prototype.model.Manager;
import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.repo.ClientRepo;
import com.schedulesalon.prototype.util.UtilException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    @Override
    public Client save(Client client) throws Exception {
        Client clientFinded = find(client.getPerson());
        String[] exceptionMessageParam = {Person.objectName};
        if (clientFinded != null) {
            UtilException.throwDefault(
                    UtilException.ExceptionBuilder(
                            UtilException.THERE_IS_ALREADY_A_RECORD_WITH_THIS_DATA_WITH_PARAM,
                            exceptionMessageParam
                    )
            );
        }
        return clientRepo.save(client);
    }

    @Override
    public Client find(Person person) throws Exception {
        Long personId = person.getId();
        return clientRepo.findClientByPersonId(personId);
    }
}
