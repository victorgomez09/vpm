package com.vira.vpm.issuetrackerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.issuetrackerservice.dto.CreateProjectDto;
import com.vira.vpm.issuetrackerservice.dto.ProjectDto;
import com.vira.vpm.issuetrackerservice.dto.UpdateProjectDto;
import com.vira.vpm.issuetrackerservice.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> findAllByUserId(@RequestParam("user") String userId) {
        return ResponseEntity.ok().body(projectService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ProjectDto> create(@RequestBody CreateProjectDto data) throws AttributeException {
        return ResponseEntity.ok().body(projectService.create(data));
    }

    @PutMapping
    public ResponseEntity<ProjectDto> update(@RequestBody UpdateProjectDto data)
            throws AttributeException, NotFoundException {
        return ResponseEntity.ok().body(projectService.update(data));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Boolean> delete(@PathVariable("projectId") String projectId) throws NotFoundException {
        return ResponseEntity.ok().body(projectService.delete(projectId));
    }
}
