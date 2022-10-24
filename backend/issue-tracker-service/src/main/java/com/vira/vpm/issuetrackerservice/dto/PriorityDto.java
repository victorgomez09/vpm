package com.vira.vpm.issuetrackerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriorityDto {
    private String id;
    private String name;
    private String cardId;
}
