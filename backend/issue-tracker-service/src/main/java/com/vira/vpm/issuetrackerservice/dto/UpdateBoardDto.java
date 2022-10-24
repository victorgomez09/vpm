package com.vira.vpm.issuetrackerservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardDto {
    private String id;
    private String name;
    private String description;
    private String image;
    private List<String> users;
}
