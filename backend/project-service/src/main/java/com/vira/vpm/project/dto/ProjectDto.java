package com.vira.vpm.project.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class ProjectDto {
    @NonNull
    public String id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private String image;
    @NonNull
    private List<String> users;
    @NonNull
    private Date creationDate;
    @NonNull
    private Date updateDate;
}
