package com.vira.vpm.kanbanservice.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ProjectDto {
    private String id;
    private String name;
    private String description;
    private List<UserDto> users;
    private List<IssueDto> issues;
    private List<SprintDto> sprints;
    private Date creationDate;
    private Date updateDate;
}
