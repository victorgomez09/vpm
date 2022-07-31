package com.vira.vpm.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.users.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    public Role findByName(String name);
}
