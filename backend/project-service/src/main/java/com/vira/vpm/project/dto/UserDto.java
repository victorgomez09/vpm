package com.vira.vpm.project.dto;

import java.util.Date;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {
    @NonNull
    private String id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String fullname;

    @NonNull
    private String role;
    
    @NonNull
    private Boolean active;

    @NonNull
    private Date lastLogin;

    @NonNull
    private Date creationDate;

    @NonNull
    private Date updateDate;
}
