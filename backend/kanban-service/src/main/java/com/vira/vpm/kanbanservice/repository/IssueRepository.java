package com.vira.vpm.kanbanservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Issue;
import com.vira.vpm.kanbanservice.entity.Project;
import com.vira.vpm.kanbanservice.entity.Column;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    int countCardByColumn(Column column);

    Optional<Issue> findByNameAndColumn(String name, Column column);

    @Query(value = "SELECT i FROM Issue i WHERE i.sprint IS NULL AND i.project = ?1")
    List<Issue> findAllByProjectAndNotSprint(Project project);
}
