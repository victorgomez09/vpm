package com.vira.vpm.issuetrackerservice.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectDto {
    private String name;
    private String code;
    private String description;
    private String color;
    private List<String> users;
    private String responsible;
    private String type;
}
