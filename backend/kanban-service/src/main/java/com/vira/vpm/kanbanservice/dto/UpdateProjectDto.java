package com.vira.vpm.kanbanservice.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProjectDto {
    private String id;
    private String name;
    private String description;
    private List<String> users;
}
