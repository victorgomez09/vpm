package com.vira.vpm.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.project.dto.ProjectDto;
import com.vira.vpm.project.model.Project;
import com.vira.vpm.project.model.ProjectUser;
import com.vira.vpm.project.repository.ProjectRepository;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectUserService projectUserService;

    public List<ProjectDto> findAllByUser(String userId) {
        List<ProjectUser> projectUserList = projectUserService.findAllByUser(userId);
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
            
            List<ProjectUser> projectUserSet = new ArrayList<>();
            for (String user : projectData.getUsers()) {
                ProjectUser projectUser = projectUserService.save(projectToSave, user);
                projectUserSet.add(projectUser);
            }
            projectToSave.setUsers(projectUserSet);
            projectRepository.save(projectToSave);

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
            projectUserService.save(project, userId);
            return new ProjectDto(project.getId(), project.getName(), project.getDescription(), project.getImage(), project.getUsers().stream().map((ProjectUser pu) -> pu.getUser()).collect(Collectors.toList()),
                project.getCreationDate(), project.getUpdateDate());
        }

        return null;
    }

    public ProjectDto removeUserFromProject(String projectId, String userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            projectUserService.delete(project, userId);
            return new ProjectDto(project.getId(), project.getName(), project.getDescription(), project.getImage(), project.getUsers().stream().map((ProjectUser pu) -> pu.getUser()).collect(Collectors.toList()),
                project.getCreationDate(), project.getUpdateDate());
        }

        return null;
    }
}
