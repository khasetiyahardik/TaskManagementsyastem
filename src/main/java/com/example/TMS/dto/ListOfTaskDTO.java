package com.example.TMS.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListOfTaskDTO {

    private Long taskId;
    private String taskName;
    private String devName;
}
