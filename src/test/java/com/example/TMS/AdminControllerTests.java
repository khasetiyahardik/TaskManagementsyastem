package com.example.TMS;

import com.example.TMS.dto.AdminEntityDTO;
import com.example.TMS.dto.DeveloperEntityDTO;
import com.example.TMS.dto.ResetPasswordDTO;
import com.example.TMS.entity.AdminEntity;
import com.example.TMS.entity.DeveloperEntity;
import com.example.TMS.repository.AdminRepository;
import com.example.TMS.repository.DeveloperRepository;
import com.example.TMS.service.AdminService;
import com.example.TMS.util.ResponseMessage;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class AdminControllerTests {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    DeveloperRepository developerRepository;
    @Autowired
    AdminService adminService;

    @BeforeEach
    public void prepareData() {
        AdminEntity admin = new AdminEntity();
        admin.setAdminName("dhruman");
        admin.setAdminEmail("dhruman0507@gmail.com");
        admin.setAdminPassword("d123");
        admin.setAdminPhoneNumber("9825846578");
        admin.setUserRole("Admin");
        admin.setIsVerified(false);
        admin.setCreatedOnDate(new Date());
        admin.setUpdatedOnDate(null);
        adminRepository.save(admin);

        AdminEntity existingAdmin = adminRepository.findAll().stream().findAny().get();
        DeveloperEntity developer = new DeveloperEntity();
        developer.setDevName("Dhruv");
        developer.setDevEmail("dhruman1845@gmail.com");
        developer.setDevMobile("9575312458");
        developer.setDevPassword("dhruv123");
        developer.setAddedOn(new Date());
        developer.setUpdatedOn(null);
        developer.setUserRole("Developer");
        developer.setIsDelete(false);
        developer.setAdmin(existingAdmin);
        developerRepository.save(developer);
    }

    @AfterEach
    public void deleteData() {
        developerRepository.deleteAll();
        adminRepository.deleteAll();

    }

    @Test
    public void testSaveAdmin() throws MessagingException {
        AdminEntityDTO adminEntityDTO = new AdminEntityDTO();
        adminEntityDTO.setAdminName("Sahil");
        adminEntityDTO.setAdminEmail("190770107512@socet.edu.in");
        adminEntityDTO.setAdminPassword("d123");
        adminEntityDTO.setAdminPhoneNumber("6352428437");
        adminEntityDTO.setUserRole("Admin");
        Assertions.assertEquals(ResponseMessage.ADDED_SUCCESSFULLY, adminService.registerAdmin(adminEntityDTO).getMessage());
    }

    @Test
    public void testGetAllAdmin() {
        List<AdminEntity> adminEntityList = adminRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(adminEntityList).size().isGreaterThan(0);
    }

    @Test
    public void testGetAdminById() {
        Long adminId = adminRepository.findAll().stream().findAny().get().getAdminId();
        Assertions.assertEquals(ResponseMessage.FETCHED_SUCCESSFULLY, adminService.getAdmin(adminId).getMessage());
    }

    @Test
    public void testResetPassword() {
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
        resetPasswordDTO.setAdminEmail("dhruman0507@gmail.com");
        resetPasswordDTO.setAdminPassword("d123");
        resetPasswordDTO.setNewPassword("d1234");
        Assertions.assertEquals(ResponseMessage.RESET_PASSWORD_SUCCESSFULLY, adminService.resetPassword(resetPasswordDTO).getMessage());
    }

    @Test
    public void testForgotPassword() throws MessagingException {
        String adminEmail = adminRepository.findAll().stream().findAny().get().getAdminEmail();
        Assertions.assertEquals(ResponseMessage.EMAIL_SEND_SUCCESSFULLY, adminService.forgotPassword(adminEmail).getMessage());
    }

    @Test
    public void testVerifyAdmin() {
        String adminEmail = adminRepository.findAll().stream().findAny().get().getAdminEmail();
        String adminPassword = adminRepository.findAll().stream().findAny().get().getAdminPassword();
        Assertions.assertEquals(ResponseMessage.VERIFIED_SUCCESSFULLY, adminService.verifyAdmin(adminEmail, adminPassword).getMessage());
    }

    @Test
    public void testAddDeveloper() throws MessagingException {
        Long adminId = adminRepository.findAll().stream().findAny().get().getAdminId();
        DeveloperEntityDTO developerEntityDTO = new DeveloperEntityDTO();
        developerEntityDTO.setDevName("Hardik");
        developerEntityDTO.setDevEmail("190770107512@socet.edu.in");
        developerEntityDTO.setDevMobile("9575642589");
        developerEntityDTO.setAdminId(String.valueOf(adminId));
        Assertions.assertEquals(ResponseMessage.ADDED_SUCCESSFULLY, adminService.addDeveloper(developerEntityDTO).getMessage());
    }

    @Test
    public void testUpdateDeveloper() {
        Long adminId = adminRepository.findAll().stream().findAny().get().getAdminId();
        Long devId = developerRepository.findAll().stream().findAny().get().getDevId();
        DeveloperEntityDTO developerEntityDTO = new DeveloperEntityDTO();
        developerEntityDTO.setDevName("Aksh");
        developerEntityDTO.setDevEmail("dhruman1845@gmail.com");
        developerEntityDTO.setDevMobile("9575312458");
        developerEntityDTO.setAdminId(String.valueOf(adminId));
        Assertions.assertEquals(ResponseMessage.UPDATED_SUCCESSFULLY, adminService.updateDeveloper(developerEntityDTO, devId).getMessage());
    }

    @Test
    public void testRemoveDeveloper() {
        Long devId = developerRepository.findAll().stream().findAny().get().getDevId();
        Assertions.assertEquals(ResponseMessage.REMOVED_SUCCESSFULLY, adminService.removeDeveloper(devId).getMessage());
    }

    @Test
    public void testDevForgotPassword() throws MessagingException {
        String devEmailId = developerRepository.findAll().stream().findAny().get().getDevEmail();
        Assertions.assertEquals(ResponseMessage.EMAIL_SEND_SUCCESSFULLY, adminService.devForgotPassword(devEmailId).getMessage());
    }
}