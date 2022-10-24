package com.vira.vpm.issuetrackerservice.dto;

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
    private String code;
    private String description;
    private String color;
    private List<UserDto> users;
    private UserDto responsible;
    private List<IssueDto> issues;
    private List<SprintDto> sprints;
    private String type;
    private Date creationDate;
    private Date updateDate;
}
