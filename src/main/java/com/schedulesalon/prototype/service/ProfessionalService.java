package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Professional;

public interface ProfessionalService {
    Professional save(Professional professional) throws Exception;
    Professional find(Person person) throws Exception;
}
