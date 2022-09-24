package com.vira.vpm.kanbanservice.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.CreatePriorityDto;
import com.vira.vpm.kanbanservice.dto.PriorityDto;
import com.vira.vpm.kanbanservice.entity.Issue;
import com.vira.vpm.kanbanservice.entity.Priority;
import com.vira.vpm.kanbanservice.repository.IssueRepository;
import com.vira.vpm.kanbanservice.repository.PriorityRepository;
import com.vira.vpm.kanbanservice.util.EnumUtil;

@Service
public class PriorityService {

    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private EnumUtil enumUtil;

    public PriorityDto create(CreatePriorityDto data) throws NotFoundException {
        Optional<Issue> issue = issueRepository.findById(data.getCardId());
        if (!issue.isPresent()) {
            throw new NotFoundException("Card with id '" + data.getCardId() + "' not found");
        }
        Priority priority = priorityRepository
                .save(Priority.builder().name(enumUtil.parsePriorityStringToEnum(data.getName()))
                        .issues(Arrays.asList(issue.get()))
                        .build());
        return PriorityDto.builder().id(priority.getId()).name(priority.getName().name())
                .cardId(issue.get().getId()).build();
    }
}
