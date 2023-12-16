package com.example.TMS.service;

import com.example.TMS.entity.AdminEntity;
import com.example.TMS.entity.DeveloperEntity;
import jakarta.mail.MessagingException;

public interface EmailService {
    public void loginEmail(DeveloperEntity developerEntity) throws MessagingException;

    public void resetPasswordEmail(AdminEntity adminEntity) throws MessagingException;

    public void devForgotPasswordEmail(DeveloperEntity developerEntity) throws MessagingException;

    public void sendVerifyEmail(AdminEntity adminEntity) throws MessagingException;
}