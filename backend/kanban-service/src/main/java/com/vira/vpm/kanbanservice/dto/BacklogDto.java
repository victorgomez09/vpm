package com.vira.vpm.kanbanservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class BacklogDto {
    private ProjectDto project;
    private List<SprintDto> sprints;
    private List<IssueDto> issues;
}
