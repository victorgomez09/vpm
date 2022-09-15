package com.vira.vpm.kanbanservice.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCardDto {
    private String name;
    private String description;
    private List<UserDto> users;
}
