package com.example.TMS.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SprintEntityDTO {
    @NotEmpty(message = "Name is required to create a sprint")
    private String sprintName;
    @NotEmpty(message = "Specify the starting date")
    private String sprintStartDate;
    @NotEmpty(message = "Specify the ending date")
    private String sprintEndDate;
    private Long durationInDays;
    private String sprintStatus;
    @NotEmpty(message = "Task id should not be null")
    private String taskId;
}
