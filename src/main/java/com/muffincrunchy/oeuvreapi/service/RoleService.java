package com.muffincrunchy.oeuvreapi.service;


import com.muffincrunchy.oeuvreapi.model.constant.UserRole;
import com.muffincrunchy.oeuvreapi.model.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}

