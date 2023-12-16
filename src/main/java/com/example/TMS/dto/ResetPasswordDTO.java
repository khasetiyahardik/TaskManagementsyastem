package com.example.TMS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResetPasswordDTO {
    @NotEmpty(message = "Email should not be empty")
    @Pattern(message = "Write email in proper format", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$")
    private String adminEmail;
    @NotEmpty(message = "Password should not be empty")
    private String adminPassword;
    @NotEmpty(message = "Password should not be empty")
    private String newPassword;
}
