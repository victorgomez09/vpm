package com.vira.vpm.issuetrackerservice.dto;

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
