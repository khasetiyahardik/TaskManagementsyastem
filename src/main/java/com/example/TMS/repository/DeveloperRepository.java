package com.example.TMS.repository;

import com.example.TMS.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {
    @Query(value = "SELECT * FROM developer WHERE dev_email=:devEmail OR dev_mobile=:devMobile", nativeQuery = true)
    Optional<DeveloperEntity> findByDevEmailAndMobile(String devEmail, String devMobile);

    @Query(value = "SELECT * FROM developer WHERE admin_id=:adminId AND is_delete = false", nativeQuery = true)
    List<DeveloperEntity> findAllDevelopersByAdminId(Long adminId);

    @Query(value = "SELECT * FROM developer WHERE dev_email=:devEmail", nativeQuery = true)
    Optional<DeveloperEntity> findByEmail(String devEmail);

    @Query(value = "SELECT * FROM developer WHERE dev_id=:devId", nativeQuery = true)
    DeveloperEntity findByDeveloperId(Long devId);

    @Query(value = "SELECT * FROM developer WHERE dev_name=:devName", nativeQuery = true)
    DeveloperEntity findByDeveloperName(String devName);
}