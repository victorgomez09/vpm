package com.vira.vpm.kanbanservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Column;

@Repository
public interface ColumnRepository extends JpaRepository<Column, String> {
    Optional<Column> findByName(String name);
    int countColumnsByBoard(String boardId);
}
