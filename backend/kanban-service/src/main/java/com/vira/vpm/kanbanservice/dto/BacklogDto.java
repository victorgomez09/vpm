package com.vira.vpm.kanbanservice.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BacklogDto {
    private ProjectDto project;
    private List<SprintDto> sprints;
    private List<IssueDto> issues;
}
