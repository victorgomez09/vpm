package com.vira.vpm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.project.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> { }
