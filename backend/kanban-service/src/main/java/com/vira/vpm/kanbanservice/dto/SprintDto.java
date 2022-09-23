package com.vira.vpm.kanbanservice.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintDto {
    private String id;
    private String name;
    private List<UserDto> users;
    private List<IssueDto> issues;
    private BoardDto board;
    private Date startDate;
    private Date endDate;
    private String objective;
    private Date creationDate;
    private Date updateDate;
}
