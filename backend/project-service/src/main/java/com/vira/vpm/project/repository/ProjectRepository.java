package com.vira.vpm.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.project.model.Project;
import com.vira.vpm.project.model.ProjectUser;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    public List<Project> findByUsersIn(List<ProjectUser> users);
    public Project findByName(String name);
}
