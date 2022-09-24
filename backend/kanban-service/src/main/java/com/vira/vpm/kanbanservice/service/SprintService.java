package com.vira.vpm.kanbanservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.CreateSprintDto;
import com.vira.vpm.kanbanservice.dto.ProjectDto;
import com.vira.vpm.kanbanservice.dto.SprintDto;
import com.vira.vpm.kanbanservice.entity.Project;
import com.vira.vpm.kanbanservice.entity.Sprint;
import com.vira.vpm.kanbanservice.feign.UserFeign;
import com.vira.vpm.kanbanservice.repository.ProjectRepository;
import com.vira.vpm.kanbanservice.repository.SprintRepository;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserFeign userFeign;

    public SprintDto findById(String id) throws NotFoundException {
        Optional<Sprint> sprint = sprintRepository.findById(id);
        if (!sprint.isPresent()) {
            throw new NotFoundException("Sprint with id '" + id + "' not exists");
        }
        return SprintDto.builder().id(sprint.get().getId()).name(sprint.get().getName())
                .startDate(sprint.get().getStartDate())
                .endDate(sprint.get().getEndDate())
                .project(ProjectDto.builder().id(sprint.get().getProject().getId())
                        .name(sprint.get().getProject().getName())
                        .description(sprint.get().getProject().getDescription())
                        .users(userFeign.findAllUsersByIds(sprint.get().getProject().getUsers()))
                        .creationDate(sprint.get().getProject().getCreationDate())
                        .updateDate(sprint.get().getProject().getUpdateDate()).build())
                .creationDate(sprint.get().getProject().getCreationDate())
                .updateDate(sprint.get().getProject().getUpdateDate())
                .build();
    }

    public SprintDto create(CreateSprintDto data) throws AttributeException, NotFoundException {
        if (sprintRepository.findByName(data.getName()).isPresent()) {
            throw new AttributeException("Sprint with name '" + data.getName() + "' already exits");
        }
        Optional<Project> project = projectRepository.findById(data.getProjectId());
        if (!project.isPresent()) {
            throw new NotFoundException("Project with id '" + data.getProjectId() + "' not exists");
        }
        Sprint sprint = sprintRepository.save(Sprint.builder().name(data.getName()).startDate(data.getStartDate())
                .endDate(data.getEndDate()).objective(data.getObjective()).project(project.get()).build());
        return SprintDto.builder().id(sprint.getId()).name(sprint.getName()).startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .project(ProjectDto.builder().id(project.get().getId()).name(project.get().getName())
                        .description(project.get().getDescription())
                        .users(userFeign.findAllUsersByIds(project.get().getUsers()))
                        .creationDate(project.get().getCreationDate())
                        .updateDate(project.get().getUpdateDate()).build())
                .creationDate(sprint.getCreationDate()).updateDate(sprint.getUpdateDate())
                .build();
    }
}
