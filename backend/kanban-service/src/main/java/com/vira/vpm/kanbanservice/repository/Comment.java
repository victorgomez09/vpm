package com.vira.vpm.kanbanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Comment extends JpaRepository<Comment, String> {}
