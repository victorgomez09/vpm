package com.vira.vpm.kanbanservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.kanbanservice.dto.CreateProjectDto;
import com.vira.vpm.kanbanservice.dto.ProjectDto;
import com.vira.vpm.kanbanservice.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDto> create(@RequestBody CreateProjectDto data) throws AttributeException {
        return ResponseEntity.ok().body(projectService.create(data));
    }
}
