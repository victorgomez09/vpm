package com.vira.vpm.kanbanservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    Optional<Project> findByName(String name);

    List<Project> findByUsersIn(List<String> users);
}
