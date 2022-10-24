package com.vira.vpm.issuetrackerservice.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBoardDto {
    private String name;
    private String description;
    private String image;
    private List<String> users;
}
