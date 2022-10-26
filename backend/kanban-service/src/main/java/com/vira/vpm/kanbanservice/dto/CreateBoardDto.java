package com.vira.vpm.kanbanservice.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBoardDto {
    private String name;
    private String code;
    private String description;
    private String color;
    private List<String> users;
}
