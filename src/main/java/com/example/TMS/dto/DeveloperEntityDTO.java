package com.example.TMS.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeveloperEntityDTO {

    private String userRole;
    @NotEmpty(message = "Name should not be empty")
    private String devName;
    @NotEmpty(message = "Phone number should not be null")
    @Size(min = 10, max = 10, message = "Mobile number should be 10 digits")
    private String devMobile;
    @NotEmpty(message = "Email should not be empty")
    @Pattern(message = "Write email in proper format", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$")
    private String devEmail;
    private Date addedOn;
    private Date updatedOn;
    private Boolean isDelete;
    @NotEmpty(message = "Admin id should not be empty")
    private String adminId;

}
