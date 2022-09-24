package com.vira.vpm.kanbanservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSprintDto {
    private String name;
    private Date startDate;
    private Date endDate;
    private String objective;
    private String projectId;
}
