package com.vira.vpm.users.dto;

import java.util.Date;

import lombok.Data;
import lombok.NonNull;

@Data
public class RoleDto {

    @NonNull
    private String id;
    
    @NonNull
    private String name;

    private Date creationDate;

    private Date updateDate;
}
