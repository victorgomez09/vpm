package com.vira.vpm.issuetrackerservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.issuetrackerservice.dto.BacklogDto;
import com.vira.vpm.issuetrackerservice.dto.IssueDto;
import com.vira.vpm.issuetrackerservice.dto.ProjectDto;
import com.vira.vpm.issuetrackerservice.dto.SprintDto;
import com.vira.vpm.issuetrackerservice.entity.Project;
import com.vira.vpm.issuetrackerservice.feign.UserFeign;
import com.vira.vpm.issuetrackerservice.repository.IssueRepository;
import com.vira.vpm.issuetrackerservice.repository.ProjectRepository;
import com.vira.vpm.issuetrackerservice.repository.SprintRepository;

@Service
public class BacklogService {

        @Autowired
        private ProjectRepository projectRepository;
        @Autowired
        private SprintRepository sprintRepository;
        @Autowired
        private IssueRepository issueRepository;
        @Autowired
        private UserFeign userFeign;

        public BacklogDto findByProjectId(String projectId) throws NotFoundException {
                Optional<Project> project = projectRepository.findById(projectId);
                if (!project.isPresent()) {
                        throw new NotFoundException("Project with id '" + projectId + "' not exists");
                }

                // Get all sprints for this project
                List<SprintDto> sprintDtoList = sprintRepository.findByProject(project.get()).stream()
                                .map(s -> SprintDto.builder().id(s.getId()).name(s.getName())
                                                .startDate(s.getStartDate())
                                                .endDate(s.getEndDate()).creationDate(s.getCreationDate())
                                                .updateDate(s.getUpdateDate())
                                                .build())
                                .collect(Collectors.toList());
                // Get all issues not assigned to sprint
                List<IssueDto> issueDtoList = issueRepository.findAllByProjectAndNotSprint(project.get()).stream()
                                .map(i -> IssueDto.builder().id(i.getId()).name(i.getName())
                                                .description(i.getDescription())
                                                .users(userFeign.findAllUsersByIds(i.getUsers()))
                                                .creationDate(i.getCreationDate()).updateDate(i.getUpdateDate())
                                                .build())
                                .collect(Collectors.toList());
                // Get project data
                ProjectDto projectDto = ProjectDto.builder().id(project.get().getId()).name(project.get().getName())
                                .description(project.get().getDescription())
                                .users(userFeign.findAllUsersByIds(project.get().getUsers())).build();
                // Build response object
                return BacklogDto.builder().project(projectDto).sprints(sprintDtoList).issues(issueDtoList).build();
        }
}
