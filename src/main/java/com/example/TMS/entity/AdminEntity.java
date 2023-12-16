package com.example.TMS.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "admin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String userRole;
    private String adminName;
    private String adminEmail;
    private String adminPassword;
    private String adminPhoneNumber;
    private Boolean isVerified;
    private Date createdOnDate;
    private Date updatedOnDate;
}
