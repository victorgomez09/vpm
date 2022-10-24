package com.vira.vpm.issuetrackerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.issuetrackerservice.entity.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, String> {
}
