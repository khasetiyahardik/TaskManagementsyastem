package com.example.TMS.service;

import com.example.TMS.dto.*;
import jakarta.mail.MessagingException;

public interface AdminService {
    public Response registerAdmin(AdminEntityDTO adminEntityDTO) throws MessagingException;

    public Response loginAdmin(AdminLoginDTO adminLoginDTO);

    public Response verifyAdmin(String email, String adminPassword);

    public Response getAdmin(Long adminId);

    public Response forgotPassword(String adminEmail) throws MessagingException;

    public Response resetPassword(ResetPasswordDTO resetPasswordDTO);

    public Response addDeveloper(DeveloperEntityDTO developerEntityDTO) throws MessagingException;

    public Response removeDeveloper(Long devId);

    public Response getAllDevelopers(Long adminId);

    public Response getDeveloperById(Long devId);

    public Response updateDeveloper(DeveloperEntityDTO developerEntityDTO, Long devId);

    public Response devForgotPassword(String devEmail) throws MessagingException;
}