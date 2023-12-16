package com.example.TMS.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SprintEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintId;
    private String sprintName;
    private Date sprintStartDate;
    private Date sprintEndDate;
    private Long durationInDays;
    private String sprintStatus;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<TaskEntity> taskEntity;
}
