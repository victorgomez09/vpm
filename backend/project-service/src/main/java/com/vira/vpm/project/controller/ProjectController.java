package com.vira.vpm.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.project.dto.ProjectDto;
import com.vira.vpm.project.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<ProjectDto>> findAllByUser(@RequestParam String userId) {
        try {
            List<ProjectDto> projects = projectService.findAllByUser(userId);
            if (projects == null || projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> findById(@PathVariable("projectId") String projectId) {
        try {
            ProjectDto project = projectService.findById(projectId);
            if (project == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> save(@RequestBody ProjectDto projectData) {
        try {
            ProjectDto project = projectService.save(projectData);
            if (project == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<ProjectDto> update(@RequestBody ProjectDto projectData) {
        try {
            ProjectDto project = projectService.update(projectData);
            if (project == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{projectId}/add-user/{userId}")
    public ResponseEntity<ProjectDto> addUserToProject(@PathVariable("projectId") String projectId, @PathVariable("userId") String userId) {
        try {
            ProjectDto project = projectService.addUserToProject(projectId, userId);
            if (project == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PutMapping("/{projectId}/remove-user/{userId}")
    public ResponseEntity<ProjectDto> removeUserFomProject(@PathVariable("projectId") String projectId, @PathVariable("userId") String userId) {
        try {
            ProjectDto project = projectService.removeUserFromProject(projectId, userId);
            if (project == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
