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
public class ColumnDto {
    private String id;
    private String name;
    private int order;
    private List<CardDto> cards;
    private String board;
    private Date creationDate;
    private Date updateDate;

}
