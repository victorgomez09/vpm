package com.vira.vpm.issuetrackerservice.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCardDto {
    private String name;
    private String description;
    private List<UserDto> users;
    private String priorityName;
}
