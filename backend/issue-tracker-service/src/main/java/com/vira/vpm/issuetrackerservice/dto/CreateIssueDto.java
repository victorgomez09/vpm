package com.vira.vpm.issuetrackerservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateIssueDto {
    private String name;
    private String columnId;
    private String projectId;
    private String sprintId;
}