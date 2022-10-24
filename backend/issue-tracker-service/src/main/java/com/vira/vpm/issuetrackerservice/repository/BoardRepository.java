package com.vira.vpm.issuetrackerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.issuetrackerservice.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {
    Optional<Board> findByName(String name);

    List<Board> findByUsersIn(List<String> userId);
}
