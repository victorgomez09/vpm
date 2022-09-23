package com.vira.vpm.kanbanservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.kanbanservice.dto.CreateSprintDto;
import com.vira.vpm.kanbanservice.dto.SprintDto;
import com.vira.vpm.kanbanservice.entity.Sprint;
import com.vira.vpm.kanbanservice.repository.SprintRepository;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    public SprintDto create(CreateSprintDto data) throws AttributeException {
        if (sprintRepository.findByName(data.getName()).isPresent()) {
            throw new AttributeException("Sprint with name '" + data.getName() + "' already exits");
        }
        Sprint sprint = sprintRepository.save(Sprint.builder().name(data.getName()).startDate(data.getStartDate())
                .endDate(data.getEndDate()).objective(data.getObjective()).build());
        return SprintDto.builder().id(sprint.getId()).name(sprint.getName()).startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate()).creationDate(sprint.getCreationDate()).updateDate(sprint.getUpdateDate())
                .build();
    }
}
