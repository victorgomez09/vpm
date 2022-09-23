package com.vira.vpm.kanbanservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.kanbanservice.entity.Project;
import com.vira.vpm.kanbanservice.entity.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, String> {
    Optional<Sprint> findByName(String name);

    List<Sprint> findByProject(Project project);
}
