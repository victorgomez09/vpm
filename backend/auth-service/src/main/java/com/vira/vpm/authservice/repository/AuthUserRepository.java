package com.vira.vpm.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.authservice.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, String> {

  public AuthUser findByEmail(String email);
}
