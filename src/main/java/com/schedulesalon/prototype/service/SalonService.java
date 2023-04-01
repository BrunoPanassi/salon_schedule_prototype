package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Professional;
import com.schedulesalon.prototype.model.Salon;
import com.schedulesalon.prototype.model.SalonType;

public interface SalonService {
    Salon save(Salon salon) throws Exception;
    Salon saveOrUpdate(Salon salon) throws Exception;
    Salon find(String name, String address, SalonType salonType) throws Exception;
    Salon update(Salon salon) throws Exception;
    Salon delete(Salon salon) throws Exception;
    Salon addProfessional(Professional professional, Salon salon) throws Exception;
}
