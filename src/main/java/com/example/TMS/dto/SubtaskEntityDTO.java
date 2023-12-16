package com.example.TMS.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubtaskEntityDTO {
    @NotEmpty(message = "Subtask name should not be empty")
    private String subTaskName;
    private String subTaskDescription;
    private Long subTaskPriority;
    private Long subTaskStatus;
    @NotEmpty(message = "Specify the starting date")
    private String subTaskStartDate;
    @NotEmpty(message = "Specify the ending date")
    private String subTaskEndDate;
    @Pattern(regexp = "/^(?:\\d+d(?:\\d+h(?:\\d+m)?)|\\d+[dh]?)$/", message = "Enter in this format 1d6h")
    private String subTaskLoggedHours;
    @NotEmpty(message = "Task id should not be empty")
    private String taskId;
}
