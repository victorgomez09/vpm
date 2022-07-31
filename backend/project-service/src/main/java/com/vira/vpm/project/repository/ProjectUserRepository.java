package com.vira.vpm.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.project.model.ProjectUser;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, String> {
    
    public List<ProjectUser> findByUser(String userId);
}
