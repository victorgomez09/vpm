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
import com.vira.vpm.kanbanservice.dto.IssueDto;
import com.vira.vpm.kanbanservice.dto.CreateCardDto;
import com.vira.vpm.kanbanservice.dto.UpdateCardDto;
import com.vira.vpm.kanbanservice.service.IssueService;

@RestController
@RequestMapping("/boards/card")
public class IssueController {

    @Autowired
    private IssueService cardService;

    @GetMapping("{cardId}")
    public ResponseEntity<IssueDto> findById(@PathVariable("cardId") String cardId) {
        return ResponseEntity.ok().body(cardService.findById(cardId));
    }

    @PostMapping
    public ResponseEntity<IssueDto> create(@RequestBody CreateCardDto data)
            throws NotFoundException, AttributeException {
        return ResponseEntity.ok().body(cardService.create(data));
    }

    @PutMapping("{cardId}")
    public ResponseEntity<IssueDto> update(@PathVariable("cardId") String cardId, @RequestBody UpdateCardDto data) {
        return ResponseEntity.ok().body(cardService.update(cardId, data));
    }

    @PutMapping("/sort")
    public ResponseEntity<List<IssueDto>> sortCards(@RequestBody List<IssueDto> data) throws NotFoundException {
        return ResponseEntity.ok().body(cardService.updateOrder(data));
    }

    @PutMapping("/sortWithColumns")
    public ResponseEntity<List<IssueDto>> sortCardsAndColumns(@RequestBody List<IssueDto> data) throws NotFoundException {
        return ResponseEntity.ok().body(cardService.updateOrderAndColumn(data));
    }
}