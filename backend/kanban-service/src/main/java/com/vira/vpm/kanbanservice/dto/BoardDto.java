package com.vira.vpm.kanbanservice.dto;

import java.util.Date;
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
public class BoardDto {
    private String id;
    private String name;
    private String code;
    private String description;
    private String color;
    private List<UserDto> users;
    private List<ColumnDto> columns;
    private Date creationDate;
    private Date updateDate;
}
