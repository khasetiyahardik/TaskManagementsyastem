package com.example.TMS.repository;

import com.example.TMS.entity.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SprintRepository extends JpaRepository<SprintEntity, Long> {

    @Query(value = "SELECT * FROM sprint_entity WHERE sprint_name=:sprintName", nativeQuery = true)
    SprintEntity findBySprintName(String sprintName);

    @Query(value = "SELECT COUNT(sprint_status) FROM sprint_entity WHERE sprint_status = 'ACTIVE' ", nativeQuery = true)
    Long findActiveSprint();
}