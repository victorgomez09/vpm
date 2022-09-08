package com.vira.vpm.kanbanservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.BoardDto;
import com.vira.vpm.kanbanservice.dto.CreateBoardDto;
import com.vira.vpm.kanbanservice.dto.UpdateBoardDto;
import com.vira.vpm.kanbanservice.service.BoardService;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> findAllByUser(@RequestParam("user") String user) {
       return ResponseEntity.ok().body(boardService.findAllByUser(user));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> findById(@PathVariable("boardId") String boardId) throws NotFoundException {
       return ResponseEntity.ok().body(boardService.findById(boardId));
    }

    @PostMapping
    public ResponseEntity<BoardDto> create(@RequestBody CreateBoardDto data) throws AttributeException {
        return ResponseEntity.ok().body(boardService.create(data));
    }

    @PutMapping("/{boardId}/update")
    public ResponseEntity<BoardDto> update(@RequestParam("boardId") String boardId, @RequestBody UpdateBoardDto data) throws NotFoundException {
        return ResponseEntity.ok().body(boardService.update(boardId, data));
    }

    @PutMapping("/{boardId}/update-users")
    public ResponseEntity<BoardDto> updateUsers(@RequestParam("boardId") String boardId, @RequestBody List<String> data) throws NotFoundException {
        return ResponseEntity.ok().body(boardService.updateUsers(boardId, data));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Boolean> delete(@RequestParam("boardId") String boardId) throws NotFoundException {
        return ResponseEntity.ok().body(boardService.delete(boardId));
    }
}
