package com.vira.vpm.users.dto;

import java.util.Date;
import java.util.Set;

import com.vira.vpm.users.model.Project;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class UserDto {

    @NonNull
    private String id;
    
    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String fullname;

    private String password;

    @NonNull
    private String role;

    @NonNull
    private Boolean active;

    @NonNull
    private Date lastLogin;

    @NonNull
    private Set<Project> projects;

    @NonNull
    private Date creationDate;

    @NonNull
    private Date updateDate;
}
