package com.example.TMS.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssignDeveloperDTO {
    @NotEmpty(message = "Name should not be empty")
    private String devName;
}
