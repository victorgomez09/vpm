package com.vira.vpm.kanbanservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.CreateProjectDto;
import com.vira.vpm.kanbanservice.dto.ProjectDto;
import com.vira.vpm.kanbanservice.dto.UpdateProjectDto;
import com.vira.vpm.kanbanservice.entity.Project;
import com.vira.vpm.kanbanservice.feign.UserFeign;
import com.vira.vpm.kanbanservice.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserFeign userFeign;

    public List<ProjectDto> findAllByUserId(String userId) {
        return projectRepository.findByUsersIn(Arrays.asList(userId)).stream().map(p -> ProjectDto.builder()
                .id(p.getId()).name(p.getName()).description(p.getDescription())
                .users(userFeign.findAllUsersByIds(p.getUsers()))
                .creationDate(p.getCreationDate()).updateDate(p.getUpdateDate()).build()).collect(Collectors.toList());
    }

    public ProjectDto create(CreateProjectDto data) throws AttributeException {
        if (projectRepository.findByName(data.getName()).isPresent()) {
            throw new AttributeException("Project with name '" + data.getName() + "' already exists");
        }
        Project project = projectRepository.save(Project.builder().name(data.getName())
                .description(data.getDescription()).users(data.getUsers()).build());
        return ProjectDto.builder().id(project.getId()).name(project.getName()).description(data.getDescription())
                .users(userFeign.findAllUsersByIds(project.getUsers()))
                .creationDate(project.getCreationDate())
                .updateDate(project.getUpdateDate()).build();
    }

    public ProjectDto update(UpdateProjectDto data) throws NotFoundException {
        Optional<Project> project = projectRepository.findById(data.getId());
        if (!project.isPresent()) {
            throw new NotFoundException("Project with id '" + data.getId() + "' not exists");
        }
        Project result = projectRepository.save(project.get().withName(data.getName())
                .withDescription(data.getDescription()).withUsers(data.getUsers()));
        return ProjectDto.builder().id(result.getId()).name(result.getName()).description(result.getDescription())
                .users(userFeign.findAllUsersByIds(result.getUsers()))
                .creationDate(result.getCreationDate())
                .updateDate(result.getUpdateDate()).build();
    }

    public boolean delete(String projectId) throws NotFoundException {
        Optional<Project> project = projectRepository.findById(projectId);
        if (!project.isPresent()) {
            throw new NotFoundException("Project with id '" + projectId + "' not exists");
        }
        projectRepository.delete(project.get());
        return true;
    }
}
