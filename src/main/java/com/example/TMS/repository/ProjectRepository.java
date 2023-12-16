package com.example.TMS.repository;

import com.example.TMS.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    @Query(value = "SELECT * FROM project_entity WHERE project_id=:projectId", nativeQuery = true)
    Optional<ProjectEntity> findById(Long projectId);

    @Query(value = "SELECT * FROM project_entity WHERE project_name=:projectName", nativeQuery = true)
    Optional<ProjectEntity> findByProjectName(String projectName);
}