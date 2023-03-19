package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Client;
import com.schedulesalon.prototype.model.Person;

public interface ClientService {
    Client save(Client client) throws Exception;
    Client find(Person person) throws Exception;
}
