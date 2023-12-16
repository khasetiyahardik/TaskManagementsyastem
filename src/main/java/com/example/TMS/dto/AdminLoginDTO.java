package com.example.TMS.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDTO {
    @NotEmpty(message = "Name should not be empty")
    private String adminName;
    @NotEmpty(message = "Password should not be empty")
    private String adminPassword;
}
