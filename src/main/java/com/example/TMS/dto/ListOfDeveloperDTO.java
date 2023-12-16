package com.example.TMS.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListOfDeveloperDTO {

    private String devName;
    private String devContact;
    private String devEmail;
    private String createdBy;
}
