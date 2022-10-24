package com.vira.vpm.issuetrackerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.issuetrackerservice.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
}
