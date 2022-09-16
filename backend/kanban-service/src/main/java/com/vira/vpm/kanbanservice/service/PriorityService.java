package com.vira.vpm.kanbanservice.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.CreatePriorityDto;
import com.vira.vpm.kanbanservice.dto.PriorityDto;
import com.vira.vpm.kanbanservice.entity.Card;
import com.vira.vpm.kanbanservice.entity.Priority;
import com.vira.vpm.kanbanservice.repository.CardRepository;
import com.vira.vpm.kanbanservice.repository.PriorityRepository;
import com.vira.vpm.kanbanservice.util.EnumUtil;

@Service
public class PriorityService {

    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private EnumUtil enumUtil;

    public PriorityDto create(CreatePriorityDto data) throws NotFoundException {
        Optional<Card> card = cardRepository.findById(data.getCardId());
        if (!card.isPresent()) {
            throw new NotFoundException("Card with id '" + data.getCardId() + "' not found");
        }
        Priority priority = priorityRepository
                .save(Priority.builder().name(enumUtil.parsePriorityStringToEnum(data.getName())).cards(Arrays.asList(card.get()))
                        .build());
        return PriorityDto.builder().id(priority.getId()).name(priority.getName().name())
                .cardId(card.get().getId()).build();
    }
}
