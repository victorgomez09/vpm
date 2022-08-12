package com.vira.vpm.project.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    @NonNull
    public String id;
    @NonNull
    private String name;

    private String description;

    private String image;

    @NonNull
    private List<String> users;
    @NonNull
    private Date creationDate;
    @NonNull
    private Date updateDate;
}
