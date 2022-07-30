package com.vira.vpm.users.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vira.vpm.users.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);
}
