package com.example.TMS.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListOfAdminDTO {
    private String userRole;
    @NotEmpty(message = "Name should not be empty")
    private String adminName;
    @Pattern(message = "Write email in proper format", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$")
    private String adminEmail;
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
    private String adminPhoneNumber;
    private Boolean isVerified;
    private Date createdOnDate;
    private Date updatedOnDate;
}
