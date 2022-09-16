package com.vira.vpm.kanbanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, String> {
}
