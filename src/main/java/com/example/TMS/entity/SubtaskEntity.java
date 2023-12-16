package com.example.TMS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubtaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subTaskId;
    private String subTaskName;
    private String subTaskDescription;
    private Long subTaskPriority;
    private Long subTaskStatus;
    private Date subTaskStartDate;
    private Date subTaskEndDate;
    private String subTaskLoggedHours;
    private String subTaskEstimatedTimeInDays;
    private String subTaskRemainingTimeInDays;
}
