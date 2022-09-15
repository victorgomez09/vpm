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
import com.vira.vpm.kanbanservice.dto.CardDto;
import com.vira.vpm.kanbanservice.dto.CreateCardDto;
import com.vira.vpm.kanbanservice.dto.UpdateCardDto;
import com.vira.vpm.kanbanservice.service.CardService;

@RestController
@RequestMapping("/boards/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("{cardId}")
    public ResponseEntity<CardDto> findById(@PathVariable("cardId") String cardId) {
        return ResponseEntity.ok().body(cardService.findById(cardId));
    }

    @PostMapping
    public ResponseEntity<CardDto> create(@RequestBody CreateCardDto data)
            throws NotFoundException, AttributeException {
        return ResponseEntity.ok().body(cardService.create(data));
    }

    @PutMapping("{cardId}")
    public ResponseEntity<CardDto> update(@PathVariable("cardId") String cardId, @RequestBody UpdateCardDto data) {
        return ResponseEntity.ok().body(cardService.update(cardId, data));
    }

    @PutMapping("/sort")
    public ResponseEntity<List<CardDto>> sortCards(@RequestBody List<CardDto> data) throws NotFoundException {
        return ResponseEntity.ok().body(cardService.updateOrder(data));
    }

    @PutMapping("/sortWithColumns")
    public ResponseEntity<List<CardDto>> sortCardsAndColumns(@RequestBody List<CardDto> data) throws NotFoundException {
        return ResponseEntity.ok().body(cardService.updateOrderAndColumn(data));
    }
}
