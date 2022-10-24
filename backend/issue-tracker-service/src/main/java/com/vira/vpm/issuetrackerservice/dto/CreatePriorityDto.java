package com.vira.vpm.issuetrackerservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePriorityDto {
    private String name;
    private String color;
    private String cardId;
}
