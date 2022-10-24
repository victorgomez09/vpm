package com.vira.vpm.issuetrackerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.issuetrackerservice.dto.CreatePriorityDto;
import com.vira.vpm.issuetrackerservice.dto.PriorityDto;
import com.vira.vpm.issuetrackerservice.service.PriorityService;

@RestController
@RequestMapping("/boards/priority")
public class PriorityController {

    @Autowired
    private PriorityService priorityService;

    @PostMapping
    public ResponseEntity<PriorityDto> create(@RequestBody CreatePriorityDto data) throws NotFoundException {
        return ResponseEntity.ok().body(priorityService.create(data));
    }
}
