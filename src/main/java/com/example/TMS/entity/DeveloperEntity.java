package com.example.TMS.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "developer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeveloperEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devId;
    private String userRole;
    private String devName;
    private String devMobile;
    private String devEmail;
    private String devPassword;
    private Date addedOn;
    private Date updatedOn;
    private Boolean isDelete;
    @ManyToOne
    @JoinColumn(name = "adminId")
    private AdminEntity admin;
}
