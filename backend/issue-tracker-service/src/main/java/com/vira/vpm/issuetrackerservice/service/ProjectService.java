package com.vira.vpm.issuetrackerservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.issuetrackerservice.dto.CreateProjectDto;
import com.vira.vpm.issuetrackerservice.dto.ProjectDto;
import com.vira.vpm.issuetrackerservice.dto.UpdateProjectDto;
import com.vira.vpm.issuetrackerservice.entity.Project;
import com.vira.vpm.issuetrackerservice.enums.ProjectTypeEnum;
import com.vira.vpm.issuetrackerservice.feign.UserFeign;
import com.vira.vpm.issuetrackerservice.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserFeign userFeign;

    public List<ProjectDto> findAllByUserId(String userId) {
        return projectRepository.findByUsersIn(Arrays.asList(userId)).stream().map(p -> ProjectDto.builder()
                .id(p.getId()).name(p.getName()).code(p.getCode()).description(p.getDescription())
                .color(p.getColor())
                .users(userFeign.findAllUsersByIds(p.getUsers()))
                .type(p.getType().toString())
                .creationDate(p.getCreationDate()).updateDate(p.getUpdateDate()).build()).collect(Collectors.toList());
    }

    public ProjectDto create(CreateProjectDto data) throws AttributeException {
        if (projectRepository.findByName(data.getName()).isPresent()) {
            throw new AttributeException("Project with name '" + data.getName() + "' already exists");
        }
        System.out.println("data: " + data.getType());
        Project project = projectRepository.save(Project.builder().name(data.getName())
                .code(data.getCode())
                .description(data.getDescription()).color(data.getColor()).users(data.getUsers())
                .type(ProjectTypeEnum.valueOf(data.getType()))
                .responsible(data.getResponsible()).build());
        return ProjectDto.builder().id(project.getId()).name(project.getName()).code(project.getCode())
                .description(data.getDescription())
                .color(project.getColor())
                .users(userFeign.findAllUsersByIds(project.getUsers()))
                .type(project.getType().toString())
                .creationDate(project.getCreationDate())
                .updateDate(project.getUpdateDate()).build();
    }

    public ProjectDto update(UpdateProjectDto data) throws NotFoundException {
        Optional<Project> project = projectRepository.findById(data.getId());
        if (!project.isPresent()) {
            throw new NotFoundException("Project with id '" + data.getId() + "' not exists");
        }
        Project result = projectRepository.save(project.get().withName(data.getName())
                .withDescription(data.getDescription()).withColor(data.getColor()).withUsers(data.getUsers()));
        return ProjectDto.builder().id(result.getId()).name(result.getName()).code(result.getCode())
                .description(result.getDescription())
                .color(result.getColor())
                .users(userFeign.findAllUsersByIds(result.getUsers()))
                .creationDate(result.getCreationDate())
                .type(result.toString())
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
