package com.vira.vpm.kanbanservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.kanbanservice.dto.CardDto;
import com.vira.vpm.kanbanservice.dto.ColumnDto;
import com.vira.vpm.kanbanservice.dto.CreateColumnDto;
import com.vira.vpm.kanbanservice.entity.Board;
import com.vira.vpm.kanbanservice.entity.Column;
import com.vira.vpm.kanbanservice.repository.BoardRepository;
import com.vira.vpm.kanbanservice.repository.ColumnRepository;

@Service
public class ColumnService {
    
    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private BoardRepository boardRepository;

    public ColumnDto create(CreateColumnDto data) {
        if (!columnRepository.findByName(data.getName()).isPresent()) {
            Board board = boardRepository.findByName(data.getBoardId()).get();
            int order = columnRepository.countColumnsByBoard(board.getId());
            Column column = columnRepository.save(Column.builder().name(data.getName())
                .order(order)
                .board(board)
                .build());
            return ColumnDto.builder().name(column.getName()).order(column.getOrder())
                .board(board.getId())
                .creationDate(column.getCreationDate())
                .updateDate(column.getUpdateDate())
                .build();
        } else {
            return null;
        }
    }

    public ColumnDto findById(String id) {
        Optional<Column> column = columnRepository.findById(id);
        if (!column.isPresent()) {
            return ColumnDto.builder().name(column.get().getName())
                .order(column.get().getOrder())
                .cards(column.get().getCards().stream().map(c -> CardDto.builder()
                    .name(c.getName())
                    .order(c.getOrder())
                    .build()).collect(Collectors.toList()))
                .board(column.get().getBoard().getId())
                .creationDate(column.get().getCreationDate())
                .updateDate(column.get().getUpdateDate())
                .build();
        } else {
            return null;
        }
    }

    public ColumnDto update(String id, String name) {
        Optional<Column> column = columnRepository.findById(id);
        if (column.isPresent()) {
            Column updated = columnRepository.save(column.get().withName(name));
            return ColumnDto.builder().name(updated.getName())
                .order(updated.getOrder())
                .board(updated.getBoard().getId())
                .creationDate(updated.getCreationDate())
                .updateDate(updated.getUpdateDate())
                .build();
        } else {
            return null;
        }
    }

    public ColumnDto updateOrder(String id, int order) {
        Optional<Column> column = columnRepository.findById(id);
        if (column.isPresent()) {
            Column updated = columnRepository.save(column.get().withOrder(order));
            return ColumnDto.builder().name(updated.getName())
                .order(updated.getOrder())
                .board(updated.getBoard().getId())
                .creationDate(updated.getCreationDate())
                .updateDate(updated.getUpdateDate())
                .build();
        } else {
            return null;
        }
    }

    // public ColumnDto updateUsers(String id, List<String> usersId) {
    //     Optional<Column> column = columnRepository.findById(id);
    //     if (column.isPresent()) {
    //         Column updated = columnRepository.save(column.get().withUsers(usersId));
    //         return ColumnDto.builder().name(updated.getName())
    //             .order(updated.getOrder())
    //             .board(updated.getBoard().getId())
    //             .creationDate(updated.getCreationDate())
    //             .updateDate(updated.getUpdateDate())
    //             .build();
    //     } else {
    //         return null;
    //     }
    // }

    // public List<ColumnDto> updateCardListAndOrder(List<ColumnDto> data) {
    //     List<ColumnDto> result = new ArrayList<>();
    //     for (ColumnDto columnDto : data) {
    //         Optional<Column> column = columnRepository.findById(columnDto.getId());
    //         if (column.isPresent()) {
    //             columnDto.getCards()
    //         }
    //     }
    // }
}
