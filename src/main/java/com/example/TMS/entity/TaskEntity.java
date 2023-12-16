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
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private Long taskType;
    private String taskName;
    private String taskDescription;
    private Long taskPriority;
    private Long taskStatus;
    private String loggedHours;
    private String estimatedTimeInDays;
    private String remainingTimeInDays;
    private Long taskLabel;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "projectId")
    private ProjectEntity projectEntity;
    @ElementCollection
    private List<Long> subtaskEntitiesId;
    @ManyToOne
    @JoinColumn(name = "dev_id")
    private DeveloperEntity developer;
}
