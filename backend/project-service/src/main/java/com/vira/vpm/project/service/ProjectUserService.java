package com.vira.vpm.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.project.model.Project;
import com.vira.vpm.project.model.ProjectUser;
import com.vira.vpm.project.repository.ProjectUserRepository;

@Service
public class ProjectUserService {
    
    @Autowired
    private ProjectUserRepository projectUserRepository;

    public List<ProjectUser> findAllByUser(String userId) {
        return projectUserRepository.findAllByUser(userId);
    }

    public ProjectUser save(Project project, String userId) {
        ProjectUser projectUser = projectUserRepository.findByProjectAndUser(project, userId);
        if (projectUser != null) return null;
        projectUser = new ProjectUser(project, userId);
        return projectUserRepository.save(projectUser);
    }

    public void delete(Project project, String userId) {
        ProjectUser projectUser = projectUserRepository.findByProjectAndUser(project, userId);
        if (projectUser != null) {
            projectUserRepository.delete(projectUser);
        }
    }
}
