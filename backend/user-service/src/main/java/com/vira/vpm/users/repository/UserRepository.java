package com.vira.vpm.users.repository;

import org.springframework.stereotype.Repository;

import com.vira.vpm.users.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);
    public List<User> findAllById(Iterable<String> ids);
}
