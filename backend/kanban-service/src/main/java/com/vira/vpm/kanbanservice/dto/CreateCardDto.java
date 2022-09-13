package com.vira.vpm.kanbanservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCardDto {
    private String name;
    private String columnId;
}
