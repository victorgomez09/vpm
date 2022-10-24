package com.vira.vpm.issuetrackerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vira.vpm.issuetrackerservice.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
}
