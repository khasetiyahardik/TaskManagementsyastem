package com.example.TMS.repository;

import com.example.TMS.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query(value = "SELECT * FROM task_entity WHERE task_name=:taskName", nativeQuery = true)
    Optional<TaskEntity> findByTaskName(String taskName);

    @Query(value = "SELECT * FROM task_entity WHERE project_id=:projectId", nativeQuery = true)
    List<TaskEntity> findByProjectId(Long projectId);

    @Query(value = "SELECT dev_id FROM task_entity WHERE task_id=:taskId", nativeQuery = true)
    Optional<Long> findDevId(Long taskId);
}