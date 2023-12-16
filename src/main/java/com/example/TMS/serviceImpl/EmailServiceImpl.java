package com.example.TMS.serviceImpl;

import com.example.TMS.dto.AdminEntityDTO;
import com.example.TMS.dto.DeveloperEntityDTO;
import com.example.TMS.dto.Response;
import com.example.TMS.entity.AdminEntity;
import com.example.TMS.entity.DeveloperEntity;
import com.example.TMS.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void loginEmail(DeveloperEntity developerEntity) throws MessagingException {
        logger.info("EmailServiceImpl : loginEmail");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(email);
            helper.setTo(developerEntity.getDevEmail());
            helper.setSubject("TMS Login");
            helper.setText("Click here to login " + "http://localhost:8888/admin/login " + "\nYour login credentials are " + "\nUserName: " + developerEntity.getDevName() + "\nPassword: " + developerEntity.getDevPassword());
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Problem occured while sending email");
        }
    }

    @Override
    public void resetPasswordEmail(AdminEntity adminEntity) throws MessagingException {
        logger.info("EmailServiceImpl : resetPasswordEmail");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(email);
            helper.setTo(adminEntity.getAdminEmail());
            helper.setSubject("TMS Reset password");
            helper.setText("Click here to create a new password: " + "\nhttp://localhost:8888/admin/reset?oldpassword=" + adminEntity.getAdminPassword() + "&username=" + adminEntity.getAdminName());
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Problem occured while sending email");
        }
    }

    @Override
    public void devForgotPasswordEmail(DeveloperEntity developerEntity) throws MessagingException {
        logger.info("EmailServiceImpl : devForgotPasswordEmail");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email);
            message.setTo(developerEntity.getDevEmail());
            message.setSubject("TMS Forgot password");
            message.setText("Your password is " + developerEntity.getDevPassword() + "&username=" + developerEntity.getDevName());
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Problem occured while sending email");
        }
    }

    @Override
    public void sendVerifyEmail(AdminEntity adminEntity) throws MessagingException {
        logger.info("EmailServiceImpl : sendVerifyEmail");
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(email);
            helper.setTo(adminEntity.getAdminEmail());
            helper.setSubject("verify");
            helper.setText("Click here to verify " + "http://localhost:8888/admin/verify?email=" + adminEntity.getAdminEmail() + "&password=" + adminEntity.getAdminPassword());
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("Problem occured while sending email");
        }
    }
}