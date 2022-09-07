package com.vira.vpm.kanbanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {}
