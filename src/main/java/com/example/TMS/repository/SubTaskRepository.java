package com.example.TMS.repository;

import com.example.TMS.entity.SubtaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubTaskRepository extends JpaRepository<SubtaskEntity, Long> {
    @Query(value = "SELECT * FROM subtask_entity WHERE sub_task_name=:subTaskName", nativeQuery = true)
    Optional<SubtaskEntity> findBySubtaskName(String subTaskName);
}