package com.vira.vpm.kanbanservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Card;
import com.vira.vpm.kanbanservice.entity.Column;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    int countCardByColumn(Column column);

    Optional<Card> findByNameAndColumn(String name, Column column);
}
