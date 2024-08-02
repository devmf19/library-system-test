package com.cloudlabs.library.service;

import com.cloudlabs.library.enums.RoleEnum;
import com.cloudlabs.library.model.Role;

public interface RoleService {
    Role readByName(RoleEnum name);
}
