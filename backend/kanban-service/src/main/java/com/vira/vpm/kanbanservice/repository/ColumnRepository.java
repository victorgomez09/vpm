package com.vira.vpm.kanbanservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Board;
import com.vira.vpm.kanbanservice.entity.Column;

@Repository
public interface ColumnRepository extends JpaRepository<Column, String> {
    Optional<Column> findByNameAndBoard(String name, Board board);

    int countColumnsByBoard(Board board);

    List<Column> findAllByBoard(String boardId);
}
