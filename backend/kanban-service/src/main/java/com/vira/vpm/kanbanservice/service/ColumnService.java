package com.vira.vpm.kanbanservice.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.IssueDto;
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

    public ColumnDto create(CreateColumnDto data) throws AttributeException {
        Board board = boardRepository.findById(data.getBoardId()).get();
        if (columnRepository.findByNameAndBoard(data.getName(), board).isPresent()) {
            throw new AttributeException("Column with name '" + data.getName() + "' exists");
        }
        int order = columnRepository.countColumnsByBoard(board);
        Column column = columnRepository.save(Column.builder().name(data.getName())
                .order(order)
                .board(board)
                .build());
        return ColumnDto.builder()
                .id(column.getId())
                .name(column.getName()).order(column.getOrder())
                .board(board.getId())
                .creationDate(column.getCreationDate())
                .updateDate(column.getUpdateDate())
                .build();
    }

    public List<ColumnDto> findAllByBoardId(String boardId) {
        return columnRepository.findAllByBoard(boardId).stream().map(column -> ColumnDto.builder()
                .name(column.getName())
                .order(column.getOrder())
                .cards(column.getCards().stream().map(c -> IssueDto.builder()
                        .name(c.getName())
                        .order(c.getOrder())
                        .build()).collect(Collectors.toList()))
                .board(column.getBoard().getId())
                .creationDate(column.getCreationDate())
                .updateDate(column.getUpdateDate())
                .build()).collect(Collectors.toList());
    }

    public ColumnDto findById(String id) {
        Optional<Column> column = columnRepository.findById(id);
        if (!column.isPresent()) {
            return ColumnDto.builder().name(column.get().getName())
                    .order(column.get().getOrder())
                    .cards(column.get().getCards().stream().map(c -> IssueDto.builder()
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

    public List<ColumnDto> updateOrder(List<ColumnDto> data) throws NotFoundException {
        List<ColumnDto> result = new ArrayList<>();
        for (ColumnDto element : data) {
            Optional<Column> column = columnRepository.findById(element.getId());
            if (!column.isPresent()) {
                throw new NotFoundException("Column with id '"+element.getId()+"' not found");
            }
            Column updated = columnRepository.save(column.get().withOrder(element.getOrder()));
            result.add(ColumnDto.builder().name(updated.getName())
                    .order(updated.getOrder())
                    .board(updated.getBoard().getId())
                    .creationDate(updated.getCreationDate())
                    .updateDate(updated.getUpdateDate())
                    .build());
        }
        result.sort(Comparator.comparingInt(ColumnDto::getOrder));
        return result;
    }

    // public ColumnDto updateUsers(String id, List<String> usersId) {
    // Optional<Column> column = columnRepository.findById(id);
    // if (column.isPresent()) {
    // Column updated = columnRepository.save(column.get().withUsers(usersId));
    // return ColumnDto.builder().name(updated.getName())
    // .order(updated.getOrder())
    // .board(updated.getBoard().getId())
    // .creationDate(updated.getCreationDate())
    // .updateDate(updated.getUpdateDate())
    // .build();
    // } else {
    // return null;
    // }
    // }

    // public List<ColumnDto> updateCardListAndOrder(List<ColumnDto> data) {
    // List<ColumnDto> result = new ArrayList<>();
    // for (ColumnDto columnDto : data) {
    // Optional<Column> column = columnRepository.findById(columnDto.getId());
    // if (column.isPresent()) {
    // columnDto.getCards()
    // }
    // }
    // }
}
