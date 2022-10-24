package com.vira.vpm.issuetrackerservice.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProjectDto {
    private String id;
    private String name;
    private String description;
    private String color;
    private List<String> users;
}