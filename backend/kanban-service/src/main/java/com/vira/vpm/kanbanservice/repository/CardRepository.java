package com.vira.vpm.kanbanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    int countCardByColumn(String columnId);
}
