package com.vira.vpm.kanbanservice.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vira.vpm.common.exception.AttributeException;
import com.vira.vpm.common.exception.NotFoundException;
import com.vira.vpm.kanbanservice.dto.BoardDto;
import com.vira.vpm.kanbanservice.dto.CardDto;
import com.vira.vpm.kanbanservice.dto.ColumnDto;
import com.vira.vpm.kanbanservice.dto.CreateBoardDto;
import com.vira.vpm.kanbanservice.dto.PriorityDto;
import com.vira.vpm.kanbanservice.dto.UpdateBoardDto;
import com.vira.vpm.kanbanservice.dto.UserDto;
import com.vira.vpm.kanbanservice.entity.Board;
import com.vira.vpm.kanbanservice.entity.Card;
import com.vira.vpm.kanbanservice.entity.Column;
import com.vira.vpm.kanbanservice.feign.UserFeign;
import com.vira.vpm.kanbanservice.repository.BoardRepository;

@Service
public class BoardService {

        @Autowired
        private BoardRepository boardRepository;
        @Autowired
        private UserFeign userFeign;

        public List<BoardDto> findAllByUser(String userId) {
                List<Board> boards = boardRepository.findAllByUsers(userId);
                return boards.stream().map(b -> BoardDto.builder()
                                .id(b.getId())
                                .name(b.getName())
                                .code(b.getCode())
                                .description(b.getDescription())
                                .color(b.getColor())
                                .users(userFeign.findAllUsersByIds(b.getUsers()))
                                .columns(b.getColumns().stream().map(c -> ColumnDto.builder()
                                                .name(c.getName())
                                                .order(c.getOrder())
                                                .build()).collect(Collectors.toList()))
                                .creationDate(b.getCreationDate())
                                .updateDate(b.getUpdateDate())
                                .build()).collect(Collectors.toList());
        }

        public BoardDto findById(String id) throws NotFoundException {
                Optional<Board> board = boardRepository.findById(id);
                if (!board.isPresent()) {
                        throw new NotFoundException("Board with id '" + id + "' not found");
                }
                List<UserDto> users = userFeign.findAllUsersByIds(board.get().getUsers());
                return BoardDto.builder()
                                .id(board.get().getId())
                                .name(board.get().getName())
                                .code(board.get().getCode())
                                .description(board.get().getDescription())
                                .color(board.get().getColor())
                                .users(users)
                                .columns(board.get().getColumns().stream()
                                                .sorted(Comparator.comparingInt(Column::getOrder))
                                                .map(c -> ColumnDto.builder()
                                                                .id(c.getId())
                                                                .name(c.getName())
                                                                .order(c.getOrder())
                                                                .cards(c.getCards().stream()
                                                                                .sorted(Comparator.comparingInt(
                                                                                                Card::getOrder))
                                                                                .map(card -> CardDto.builder()
                                                                                                .id(card.getId())
                                                                                                .name(card.getName())
                                                                                                .description(card
                                                                                                                .getDescription())
                                                                                                .columnId(c.getId())
                                                                                                .users(userFeign.findAllUsersByIds(
                                                                                                                card.getUsers()))
                                                                                                .priority(PriorityDto
                                                                                                                .builder()
                                                                                                                .id(card.getPriority()
                                                                                                                                .getId())
                                                                                                                .name(card.getPriority()
                                                                                                                                .getName()
                                                                                                                                .name())
                                                                                                                .cardId(card.getPriority()
                                                                                                                                .getId())
                                                                                                                .build())
                                                                                                .build())
                                                                                .collect(Collectors.toList()))
                                                                .board(c.getBoard().getId())
                                                                .creationDate(c.getCreationDate())
                                                                .updateDate(c.getUpdateDate())
                                                                .build())
                                                .collect(Collectors.toList()))
                                .creationDate(board.get().getCreationDate())
                                .updateDate(board.get().getUpdateDate())
                                .build();
        }

        public BoardDto create(CreateBoardDto data) throws AttributeException {
                if (boardRepository.findByName(data.getName()).isPresent()) {
                        throw new AttributeException("Board with name '" + data.getName() + "' already exists");
                }
                Board board = Board.builder()
                                .name(data.getName())
                                .code(data.getCode())
                                .description(data.getDescription())
                                .color(data.getColor())
                                .users(data.getUsers())
                                .build();
                Board result = boardRepository.save(board);
                List<UserDto> users = userFeign.findAllUsersByIds(data.getUsers());
                return BoardDto.builder()
                                .id(result.getId())
                                .name(result.getName())
                                .code(result.getCode())
                                .description(result.getDescription())
                                .color(result.getColor())
                                .users(users)
                                .creationDate(result.getCreationDate())
                                .updateDate(result.getUpdateDate())
                                .build();
        }

        public BoardDto update(String id, UpdateBoardDto data) throws NotFoundException {
                Optional<Board> board = boardRepository.findById(id);
                if (!board.isPresent()) {
                        throw new NotFoundException("Board with id '" + id + "' not found");
                }
                Board updated = board.get()
                                .withName(data.getName())
                                .withDescription(data.getDescription())
                                .withColor(data.getColor())
                                .withUsers(data.getUsers());
                Board result = boardRepository.save(updated);
                List<UserDto> users = userFeign.findAllUsersByIds(result.getUsers());
                return BoardDto.builder()
                                .id(result.getId())
                                .name(result.getName())
                                .color(result.getCode())
                                .description(result.getDescription())
                                .color(result.getColor())
                                .users(users)
                                .columns(result.getColumns().stream().map(c -> ColumnDto.builder()
                                                .name(c.getName())
                                                .order(c.getOrder())
                                                .cards(c.getCards().stream().map(card -> CardDto.builder()
                                                                .id(card.getId())
                                                                .name(card.getName())
                                                                .description(card.getDescription())
                                                                .priority(PriorityDto.builder()
                                                                                .id(card.getPriority().getId())
                                                                                .name(card.getPriority().getName()
                                                                                                .name())
                                                                                .build())
                                                                .columnId(card.getColumn().getId())
                                                                .users(userFeign.findAllUsersByIds(card.getUsers()))
                                                                .creationDate(card.getCreationDate())
                                                                .updateDate(card.getUpdateDate())
                                                                .build()).collect(Collectors.toList()))
                                                .build()).collect(Collectors.toList()))
                                .creationDate(result.getCreationDate())
                                .updateDate(result.getUpdateDate())
                                .build();
        }

        public BoardDto updateUsers(String id, List<String> usersId) throws NotFoundException {
                Optional<Board> board = boardRepository.findById(id);
                if (!board.isPresent()) {
                        throw new NotFoundException("Board with id '" + id + "' not found");
                }
                Board result = boardRepository.save(board.get().withUsers(usersId));
                List<UserDto> users = userFeign.findAllUsersByIds(result.getUsers());
                return BoardDto.builder()
                                .id(result.getId())
                                .name(result.getName())
                                .code(result.getCode())
                                .description(result.getDescription())
                                .color(result.getColor())
                                .users(users)
                                .columns(result.getColumns().stream().map(c -> ColumnDto.builder()
                                                .name(c.getName())
                                                .order(c.getOrder())
                                                .build()).collect(Collectors.toList()))
                                .creationDate(result.getCreationDate())
                                .updateDate(result.getUpdateDate())
                                .build();
        }

        public boolean delete(String id) throws NotFoundException {
                Optional<Board> board = boardRepository.findById(id);
                if (!board.isPresent()) {
                        throw new NotFoundException("Board with id '" + id + "' not found");
                }
                boardRepository.delete(board.get());
                return true;
        }
}
