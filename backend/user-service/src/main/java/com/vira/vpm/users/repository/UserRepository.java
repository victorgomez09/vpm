package com.vira.vpm.users.repository;

import org.springframework.stereotype.Repository;

import com.vira.vpm.users.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);
}
