package com.vira.vpm.authservice.dto;

import lombok.Data;

@Data
public class UserDto {
    
    private String id;
    private String email;
    private String fullname;
    private String image;
}
