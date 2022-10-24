package com.vira.vpm.issuetrackerservice.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {
    private String id;
    private String name;
    private String description;
    private int order;
    private List<UserDto> users;
    private List<TagDto> tags;
    private PriorityDto priority;
    private List<CommentDto> comments;
    private ProjectDto project;
    private SprintDto sprint;
    private String columnId;
    private Date creationDate;
    private Date updateDate;
}
