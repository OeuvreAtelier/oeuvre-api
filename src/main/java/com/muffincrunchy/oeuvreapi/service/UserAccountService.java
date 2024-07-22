package com.muffincrunchy.oeuvreapi.service;

import com.muffincrunchy.oeuvreapi.model.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {
    UserAccount getById(String id);
    UserAccount getByContext();
}

