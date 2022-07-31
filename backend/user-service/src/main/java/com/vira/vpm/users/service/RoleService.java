package com.vira.vpm.users.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.users.dto.RoleDto;
import com.vira.vpm.users.model.Role;
import com.vira.vpm.users.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;


    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map((Role role) -> new RoleDto(role.getId(), role.getName())).collect(Collectors.toList());
    }
}
