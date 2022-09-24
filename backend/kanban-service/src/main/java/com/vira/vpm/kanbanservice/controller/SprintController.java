package com.vira.vpm.kanbanservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.CreateSprintDto;
import com.vira.vpm.kanbanservice.dto.SprintDto;
import com.vira.vpm.kanbanservice.service.SprintService;

@RestController
@RequestMapping("/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @GetMapping("/{sprintId}")
    public ResponseEntity<SprintDto> findById(@PathVariable("sprintId") String id) throws NotFoundException {
        return ResponseEntity.ok().body(sprintService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SprintDto> create(@RequestBody CreateSprintDto data)
            throws AttributeException, NotFoundException {
        return ResponseEntity.ok().body(sprintService.create(data));
    }
}
