package com.vira.vpm.kanbanservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.kanbanservice.dto.CardDto;
import com.vira.vpm.kanbanservice.dto.ColumnDto;
import com.vira.vpm.kanbanservice.entity.Card;
import com.vira.vpm.kanbanservice.entity.Column;
import com.vira.vpm.kanbanservice.repository.CardRepository;
import com.vira.vpm.kanbanservice.repository.ColumnRepository;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ColumnRepository columnRepository;

    public CardDto create(String columnId, String name) {
        Optional<Column> column = columnRepository.findById(columnId);
        if (column.isPresent()) {
            int order = cardRepository.countCardByColumn(column.get().getId());
            Card card = cardRepository.save(Card.builder().name(name)
                .column(column.get())
                .order(order)
                .build());
            return CardDto.builder().id(card.getId()).name(card.getName())
                .columnDto(ColumnDto.builder().name(column.get().getName())
                    .order(column.get().getOrder())
                    .board(column.get().getBoard().getId())
                    .creationDate(column.get().getCreationDate())
                    .updateDate(column.get().getUpdateDate())
                    .build()
                ).build();
        } else {
            return null;
        }
    }

    public CardDto findById(String id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            return CardDto.builder().id(card.get().getId()).name(card.get().getName())
                .columnDto(ColumnDto.builder().name(card.get().getColumn().getName()).build())
                .description(card.get().getDescription())
                .order(card.get().getOrder())
                .creationDate(card.get().getCreationDate())
                .updateDate(card.get().getUpdateDate())
                .build();
        } else {
            return null;
        }
    }

    public CardDto update(String id, String name, String description) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            Card updateCard = cardRepository.save(card.get().withName(name).withDescription(description));
            return CardDto.builder().id(updateCard.getId()).name(updateCard.getName())
                .columnDto(ColumnDto.builder().name(updateCard.getColumn().getName()).build())
                .description(updateCard.getDescription())
                .order(updateCard.getOrder())
                .creationDate(updateCard.getCreationDate())
                .updateDate(updateCard.getUpdateDate())
                .build(); 
        } else {
            return null;
        }
    }

    public CardDto updateOrder(String id, int order) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            Card updateCard = cardRepository.save(card.get().withOrder(order));
            return CardDto.builder().id(updateCard.getId()).name(updateCard.getName())
                .columnDto(ColumnDto.builder().name(updateCard.getColumn().getName()).build())
                .description(updateCard.getDescription())
                .order(updateCard.getOrder())
                .creationDate(updateCard.getCreationDate())
                .updateDate(updateCard.getUpdateDate())
                .build(); 
        } else {
            return null;
        }
    }

    public CardDto updateColumn(String id, String columnId) {
        Optional<Card> card = cardRepository.findById(id);
        Optional<Column> column = columnRepository.findById(columnId);
        if (card.isPresent() && column.isPresent()) {
            Card updateCard = cardRepository.save(card.get().withColumn(column.get()));
            return CardDto.builder().id(updateCard.getId()).name(updateCard.getName())
                .columnDto(ColumnDto.builder().name(updateCard.getColumn().getName()).build())
                .description(updateCard.getDescription())
                .order(updateCard.getOrder())
                .creationDate(updateCard.getCreationDate())
                .updateDate(updateCard.getUpdateDate())
                .build(); 
        } else {
            return null;
        }
    }      
}
