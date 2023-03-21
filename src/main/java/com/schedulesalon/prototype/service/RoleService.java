package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;

public interface RoleService {
    Role save(Role role) throws Exception;
    Role find(String type);
}
