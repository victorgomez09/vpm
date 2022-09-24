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
public class SprintDto {
    private String id;
    private String name;
    private List<UserDto> users;
    private List<IssueDto> issues;
    private BoardDto board;
    private Date startDate;
    private Date endDate;
    private String objective;
    private ProjectDto project;
    private Date creationDate;
    private Date updateDate;
}
