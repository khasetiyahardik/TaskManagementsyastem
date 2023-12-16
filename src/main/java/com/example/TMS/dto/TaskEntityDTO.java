package com.example.TMS.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskEntityDTO {
    private Long taskType;
    @NotEmpty(message = "Task name should not be empty")
    private String taskName;
    private String taskDescription;
    private Long taskPriority;
    private Long taskStatus;
    private Long taskLabel;
    @Pattern(regexp = "/^(?:\\d+d(?:\\d+h(?:\\d+m)?)|\\d+[dh]?)$/", message = "Enter in this format 1d6h")
    private String loggedHours;
    @NotEmpty(message = "Specify the starting date")
    private String startDate;
    @NotEmpty(message = "Specify the ending date")
    private String endDate;
    @NotEmpty(message = "Project id should not be empty")
    private String projectId;
    private Long subTaskId;
    @NotEmpty(message = "Developer name should not be empty")
    private String devName;

}
