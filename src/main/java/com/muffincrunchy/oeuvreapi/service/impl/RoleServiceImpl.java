package com.muffincrunchy.oeuvreapi.service.impl;

import com.muffincrunchy.oeuvreapi.model.constant.UserRole;
import com.muffincrunchy.oeuvreapi.model.entity.Role;
import com.muffincrunchy.oeuvreapi.repository.RoleRepository;
import com.muffincrunchy.oeuvreapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role).orElseGet(() -> roleRepository.saveAndFlush(Role.builder().role(role).build()));
    }
}
