package com.vira.vpm.kanbanservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.CardDto;
import com.vira.vpm.kanbanservice.dto.CreateCardDto;
import com.vira.vpm.kanbanservice.dto.PriorityDto;
import com.vira.vpm.kanbanservice.dto.UpdateCardDto;
import com.vira.vpm.kanbanservice.dto.UserDto;
import com.vira.vpm.kanbanservice.entity.Card;
import com.vira.vpm.kanbanservice.entity.Column;
import com.vira.vpm.kanbanservice.entity.Priority;
import com.vira.vpm.kanbanservice.enums.PriorityNameEnum;
import com.vira.vpm.kanbanservice.feign.UserFeign;
import com.vira.vpm.kanbanservice.repository.CardRepository;
import com.vira.vpm.kanbanservice.repository.ColumnRepository;
import com.vira.vpm.kanbanservice.repository.PriorityRepository;
import com.vira.vpm.kanbanservice.util.EnumUtil;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private EnumUtil enumUtil;

    public CardDto create(CreateCardDto data) throws NotFoundException, AttributeException {
        Optional<Column> column = columnRepository.findById(data.getColumnId());
        if (!column.isPresent()) {
            throw new NotFoundException("Column with id '" + data.getColumnId() + "' not found");
        }
        if (cardRepository.findByNameAndColumn(data.getName(), column.get()).isPresent()) {
            throw new AttributeException("Card with name '" + data.getName() + "' exists");
        }
        int order = cardRepository.countIssueByColumn(column.get());
        Card card = cardRepository.save(Card.builder().name(data.getName())
                .column(column.get())
                .order(order)
                .build());
        Priority priority = priorityRepository
                .save(Priority.builder().name(PriorityNameEnum.MEDIUM).cards(Arrays.asList(card)).build());
        Card created = cardRepository.save(card.withPriority(priority));
        return CardDto.builder().id(created.getId()).name(created.getName())
                .columnId(created.getColumn().getId())
                .priority(PriorityDto.builder().id(priority.getId()).name(priority.getName().name()).build())
                .build();
    }

    public CardDto findById(String id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            return CardDto.builder().id(card.get().getId()).name(card.get().getName())
                    .columnId(card.get().getColumn().getId())
                    .description(card.get().getDescription())
                    .order(card.get().getOrder())
                    .users(userFeign.findAllUsersByIds(card.get().getUsers()))
                    .priority(PriorityDto.builder().id(card.get().getPriority().getId())
                            .name(card.get().getPriority().getName().name()).build())
                    .creationDate(card.get().getCreationDate())
                    .updateDate(card.get().getUpdateDate())
                    .build();
        } else {
            return null;
        }
    }

    public CardDto update(String id, UpdateCardDto data) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            Priority priority = priorityRepository.save(
                    card.get().getPriority().withName(enumUtil.parsePriorityStringToEnum(data.getPriorityName())));
            Card updateCard = cardRepository
                    .save(card.get().withName(data.getName()).withDescription(data.getDescription())
                            .withPriority(priority)
                            .withUsers(data.getUsers().stream().map(UserDto::getId).collect(Collectors.toList())));
            return CardDto.builder().id(updateCard.getId()).name(updateCard.getName())
                    .columnId(card.get().getColumn().getId())
                    .description(updateCard.getDescription())
                    .order(updateCard.getOrder())
                    .priority(PriorityDto.builder().id(updateCard.getPriority().getId())
                            .name(updateCard.getPriority().getName().name()).build())
                    .users(userFeign.findAllUsersByIds(updateCard.getUsers()))
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
                    .columnId(card.get().getColumn().getId())
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
                    .columnId(card.get().getColumn().getId())
                    .description(updateCard.getDescription())
                    .order(updateCard.getOrder())
                    .creationDate(updateCard.getCreationDate())
                    .updateDate(updateCard.getUpdateDate())
                    .build();
        } else {
            return null;
        }
    }

    public List<CardDto> updateOrder(List<CardDto> data) throws NotFoundException {
        List<CardDto> result = new ArrayList<>();
        for (CardDto element : data) {
            Optional<Card> card = cardRepository.findById(element.getId());
            if (!card.isPresent()) {
                throw new NotFoundException("Card with id '" + element.getId() + "' not found");
            }
            Card updated = cardRepository.save(card.get().withOrder(element.getOrder()));
            result.add(CardDto.builder().name(updated.getName())
                    .order(updated.getOrder())
                    .columnId(card.get().getColumn().getId())
                    .creationDate(updated.getCreationDate())
                    .updateDate(updated.getUpdateDate())
                    .build());
        }
        result.sort(Comparator.comparingInt(CardDto::getOrder));
        return result;
    }

    public List<CardDto> updateOrderAndColumn(List<CardDto> data) throws NotFoundException {
        List<CardDto> result = new ArrayList<>();
        for (CardDto element : data) {
            Optional<Card> card = cardRepository.findById(element.getId());
            if (!card.isPresent()) {
                throw new NotFoundException("Card with id '" + element.getId() + "' not found");
            }
            Optional<Column> column = columnRepository.findById(element.getColumnId());
            if (!column.isPresent()) {
                throw new NotFoundException("Column with id '" + element.getColumnId() + "' not found");
            }
            Card updated = cardRepository.save(card.get().withOrder(element.getOrder()).withColumn(column.get()));
            result.add(CardDto.builder().name(updated.getName())
                    .order(updated.getOrder())
                    .columnId(card.get().getColumn().getId())
                    .creationDate(updated.getCreationDate())
                    .updateDate(updated.getUpdateDate())
                    .build());
        }
        result.sort(Comparator.comparingInt(CardDto::getOrder));
        return result;
    }
}
