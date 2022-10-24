package com.vira.vpm.issuetrackerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.issuetrackerservice.entity.Project;
import com.vira.vpm.issuetrackerservice.entity.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, String> {
    Optional<Sprint> findByName(String name);

    List<Sprint> findByProject(Project project);
}
