package com.vira.vpm.kanbanservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.ColumnDto;
import com.vira.vpm.kanbanservice.dto.CreateColumnDto;
import com.vira.vpm.kanbanservice.service.ColumnService;

@RestController
@RequestMapping("/boards/column")
public class ColumnController {

    @Autowired
    private ColumnService columnService;

    @GetMapping("{boardId}")
    public ResponseEntity<List<ColumnDto>> findAllByBoardId(@PathVariable("boardId") String boardId) {
        return ResponseEntity.ok().body(columnService.findAllByBoardId(boardId));
    }

    @PostMapping
    public ResponseEntity<ColumnDto> create(@RequestBody CreateColumnDto data) throws AttributeException {
        return ResponseEntity.ok().body(columnService.create(data));
    }

    @PutMapping("/sort")
    public ResponseEntity<List<ColumnDto>> sortColumns(@RequestBody List<ColumnDto> data) throws NotFoundException {
        return ResponseEntity.ok().body(columnService.updateOrder(data));
    }
}
