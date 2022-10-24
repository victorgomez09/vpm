package com.vira.vpm.issuetrackerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.issuetrackerservice.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findByName(String name);

    List<Project> findByUsersIn(List<String> users);
}
