package com.example.TMS.controller;

import com.example.TMS.dto.*;
import com.example.TMS.service.AdminService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @PostMapping("/register")
    public Response registerAdmin(@RequestBody AdminEntityDTO adminEntityDTO) throws MessagingException {
        logger.info("AdminController : registerAdmin");
        return adminService.registerAdmin(adminEntityDTO);
    }

    @PostMapping("/login")
    public Response loginAdmin(@RequestBody AdminLoginDTO adminLoginDTO) {
        logger.info("AdminController : loginAdmin");
        return adminService.loginAdmin(adminLoginDTO);
    }

    @GetMapping("/verify")
    public Response verifyAdmin(@RequestParam("email") String adminEmail, @RequestParam("password") String adminPassword) {
        logger.info("AdminController : verifyAdmin");
        return adminService.verifyAdmin(adminEmail, adminPassword);

    }

    @GetMapping("/forgot")
    public Response forgotPassword(@Valid @RequestParam("adminEmail") String adminEmail) throws MessagingException {
        logger.info("AdminController : forgotPassword");
        return adminService.forgotPassword(adminEmail);
    }

    @PostMapping("/reset")
    public Response resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        logger.info("AdminController : resetPassword");
        return adminService.resetPassword(resetPasswordDTO);
    }

    @GetMapping("/getAdmin/{adminId}")
    public Response getAdmin(@PathVariable("adminId") Long adminId) {
        logger.info("AdminController : getAdmin");
        return adminService.getAdmin(adminId);
    }

    //------------------------------------------------------------------------------------------------------------------

    @PostMapping("/addDeveloper")
    public Response addDeveloper(@RequestBody DeveloperEntityDTO developerEntityDTO) throws MessagingException {
        logger.info("AdminController : addDeveloper");
        return adminService.addDeveloper(developerEntityDTO);
    }

    @GetMapping("/getAllDevelopers/{adminId}")
    public Response getAllDevelopers(@PathVariable("adminId") Long adminId) {
        logger.info("AdminController : getAllDevelopers");
        return adminService.getAllDevelopers(adminId);
    }

    @GetMapping("/getDeveloperById/{devId}")
    public Response getDeveloperById(@PathVariable("devId") Long devId) {
        logger.info("AdminController : getDeveloperById");
        return adminService.getDeveloperById(devId);
    }

    @PutMapping("/updateDeveloper/{devId}")
    public Response updateDeveloper(@RequestBody DeveloperEntityDTO developerEntityDTO, @PathVariable("devId") Long devId) {
        logger.info("AdminController : updateDeveloper");
        return adminService.updateDeveloper(developerEntityDTO, devId);

    }

    @PostMapping("/removeDeveloper/{devId}")
    public Response removeDeveloper(@PathVariable("devId") Long devId) {
        logger.info("AdminController : removeDeveloper");
        return adminService.removeDeveloper(devId);
    }

    @GetMapping("/developerForgotPassword")
    public Response devForgotPassword(@RequestParam("devEmail") String devEmail) throws MessagingException {
        logger.info("AdminController : devForgotPassword");
        return adminService.devForgotPassword(devEmail);
    }
}