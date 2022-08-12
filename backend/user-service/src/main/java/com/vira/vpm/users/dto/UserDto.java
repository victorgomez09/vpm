package com.vira.vpm.users.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Boolean active;

    @NonNull
    private Date creationDate;

    @NonNull
    private Date updateDate;
}
