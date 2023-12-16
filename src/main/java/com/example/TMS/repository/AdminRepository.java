package com.example.TMS.repository;

import com.example.TMS.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    @Query(value = "SELECT COUNT(*) FROM admin WHERE admin_phone_number=:adminPhoneNumber OR admin_email=:adminEmail OR admin_name=:adminName", nativeQuery = true)
    Long findByNoAndEmail(String adminPhoneNumber, String adminEmail, String adminName);

    @Query(value = "SELECT * FROM admin WHERE admin_email=:adminEmail AND admin_password=:adminPassword", nativeQuery = true)
    Optional<AdminEntity> findByEmailAndPassword(String adminEmail, String adminPassword);

    @Query(value = "SELECT * FROM admin WHERE admin_name=:adminName", nativeQuery = true)
    AdminEntity findByAdminName(String adminName);

    @Query(value = "SELECT * FROM admin WHERE admin_email=:adminEmail", nativeQuery = true)
    Optional<AdminEntity> findByEmail(String adminEmail);

    @Query(value = "SELECT * FROM admin WHERE admin_email=:adminEmail AND admin_password=:adminPassword", nativeQuery = true)
    Optional<AdminEntity> findByNameAndPassword(String adminEmail, String adminPassword);

    @Query(value = "SELECT * FROM admin WHERE admin_id=:adminId", nativeQuery = true)
    List<AdminEntity> findByAdminId(Long adminId);
}