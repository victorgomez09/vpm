package com.vira.vpm.kanbanservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.BacklogDto;
import com.vira.vpm.kanbanservice.service.BacklogService;

@RestController
@RequestMapping("/backlog")
public class BacklogController {

    @Autowired
    private BacklogService backlogService;

    @GetMapping("/{projectId}")
    public ResponseEntity<BacklogDto> findByProjectId(@PathVariable("projectId") String projectId)
            throws NotFoundException {
        return ResponseEntity.ok().body(backlogService.findByProjectId(projectId));
    }
}
