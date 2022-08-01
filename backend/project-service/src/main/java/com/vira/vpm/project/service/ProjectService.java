package com.vira.vpm.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.project.dto.ProjectDto;
import com.vira.vpm.project.model.Project;
import com.vira.vpm.project.model.ProjectUser;
import com.vira.vpm.project.repository.ProjectRepository;
import com.vira.vpm.project.repository.ProjectUserRepository;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectUserRepository projectUserRepository;

    public List<ProjectDto> findAllByUser(String userId) {
        List<ProjectUser> projectUserList = projectUserRepository.findAllByUser(userId);
        if (projectUserList.isEmpty()) return null;

        List<ProjectDto> projects = projectRepository.findByUsersIn(projectUserList).stream().map((Project project) -> new ProjectDto(project.getId(), 
            project.getName(), project.getDescription(), project.getImage(), project.getUsers().stream().map((ProjectUser pu) -> pu.getUser()).collect(Collectors.toList()), 
            project.getCreationDate(), project.getUpdateDate())).collect(Collectors.toList());

        return projects;
    }

    public ProjectDto findById(String projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) return null;
        return new ProjectDto(project.getId(), project.getName(), project.getDescription(), project.getImage(), project.getUsers().stream().map((ProjectUser pu) -> pu.getUser()).collect(Collectors.toList()),
            project.getCreationDate(), project.getUpdateDate());
    }


    public ProjectDto save(ProjectDto projectData) {
        Project projectToSave = projectRepository.findByName(projectData.getName());
        if (projectToSave == null) {
            projectToSave = new Project(projectData.getName(), projectData.getDescription(), projectData.getImage());
            projectRepository.save(projectToSave);

            for (String user : projectData.getUsers()) {
                ProjectUser projectUser = new ProjectUser(projectToSave, user);
                projectUserRepository.save(projectUser);
            }

            return new ProjectDto(projectToSave.getId(), projectToSave.getName(), projectToSave.getDescription(), projectToSave.getImage(), projectToSave.getUsers().stream().map((ProjectUser pu) -> pu.getUser()).collect(Collectors.toList()),
                projectToSave.getCreationDate(), projectToSave.getUpdateDate());
        };
        return null;
    }

    public ProjectDto update(ProjectDto projectData) {
        Project project = projectRepository.findById(projectData.getId()).orElse(null);
        if (project != null) {
            project.setName(projectData.getName());
            project.setDescription(projectData.getDescription());
            project.setImage(projectData.getImage());
            projectRepository.save(project);
            return projectData;
        }

        return null;
    }

    public ProjectDto addUserToProject(String projectId, String userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            List<ProjectUser> projectUserList = projectUserRepository.findByProject(project);
            for (ProjectUser e : projectUserList) {
                if (e.getUser() == userId) return null;
            }
            ProjectUser projectUser = new ProjectUser(project, userId);
            projectUserRepository.save(projectUser);
            return new ProjectDto(project.getId(), project.getName(), project.getDescription(), project.getImage(), project.getUsers().stream().map((ProjectUser pu) -> pu.getUser()).collect(Collectors.toList()),
                project.getCreationDate(), project.getUpdateDate());
        }

        return null;
    }

    public ProjectDto removeUserFromProject(String projectId, String userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            List<ProjectUser> projectUserList = projectUserRepository.findByProject(project);
            for (ProjectUser e : projectUserList) {
                if (e.getUser() != userId) return null;
            }
            ProjectUser projectUser = projectUserRepository.findByProjectAndUser(project, userId);
            projectUserRepository.delete(projectUser);
            return new ProjectDto(project.getId(), project.getName(), project.getDescription(), project.getImage(), project.getUsers().stream().map((ProjectUser pu) -> pu.getUser()).collect(Collectors.toList()),
                project.getCreationDate(), project.getUpdateDate());
        }

        return null;
    }
}
