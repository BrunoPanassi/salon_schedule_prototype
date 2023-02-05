package com.schedulesalon.prototype.service;

import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;

public interface RoleService {
    Role saveRole(Role role) throws Exception;
    Role findRole(String type);
}
