package com.vira.vpm.users.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.users.model.Project;
import com.vira.vpm.users.model.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    public List<Project> findByUsers(Set<User> users);
    
}
