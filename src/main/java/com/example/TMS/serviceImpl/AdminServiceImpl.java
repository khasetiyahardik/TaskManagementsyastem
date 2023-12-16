package com.example.TMS.serviceImpl;

import com.example.TMS.dto.*;
import com.example.TMS.entity.AdminEntity;
import com.example.TMS.entity.DeveloperEntity;
import com.example.TMS.exception.*;
import com.example.TMS.repository.AdminRepository;
import com.example.TMS.repository.DeveloperRepository;
import com.example.TMS.service.AdminService;
import com.example.TMS.service.EmailService;
import com.example.TMS.service.ValidationService;
import com.example.TMS.util.JwtUtil;
import com.example.TMS.util.ResponseMessage;
import com.example.TMS.constant.TMSConstants;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    DeveloperRepository developerRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ValidationService validationService;

    @Override
    public Response registerAdmin(AdminEntityDTO adminEntityDTO) throws MessagingException {
        logger.info("AdminServiceImpl : registerAdmin");
        Response response = validationService.validateRegisterAdminReq(adminEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        Long existingData = adminRepository.findByNoAndEmail(adminEntityDTO.getAdminPhoneNumber(), adminEntityDTO.getAdminEmail(), adminEntityDTO.getAdminName());
        AdminEntity admin = new AdminEntity();

        if (existingData == 0) {

            admin.setAdminPassword(adminEntityDTO.getAdminPassword());
            admin.setCreatedOnDate(new Date());
            admin.setIsVerified(false);
            admin.setAdminName(adminEntityDTO.getAdminName());
            admin.setAdminEmail(adminEntityDTO.getAdminEmail());
            admin.setAdminPhoneNumber(adminEntityDTO.getAdminPhoneNumber());
            admin.setUpdatedOnDate(null);
            admin.setAdminPassword(passwordEncoder.encode(adminEntityDTO.getAdminPassword()));
            admin.setUserRole(TMSConstants.DEFAULT_ADMIN_ROLE);
            adminRepository.save(admin);
            emailService.sendVerifyEmail(admin);
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, null);
        } else {
            throw new EmailAlreadyExistException(ResponseMessage.EMAIL_PHONE_NO_ALREADY_EXIST);
        }
    }

    @Override
    public Response loginAdmin(AdminLoginDTO adminLoginDTO) {
        logger.info("AdminServiceImpl : loginAdmin");
        Response response = validationService.validateLoginAdminReq(adminLoginDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }

        AdminEntity existingData = adminRepository.findByAdminName(adminLoginDTO.getAdminName());
        String token = null;
        if (existingData != null) {
            if (!existingData.getIsVerified()) {
                return new Response(ResponseMessage.BAD_REQUEST, "not verified", null);
            }
            if (passwordEncoder.matches(adminLoginDTO.getAdminPassword(), existingData.getAdminPassword())) {
                token = jwtUtil.generateJwt(adminLoginDTO);
                return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.LOGIN_SUCCESSFULLY, token);
            }
        }
        throw new CredentialsAreWrongException(ResponseMessage.WRONG_CREDENTIALS);
    }

    @Override
    public Response verifyAdmin(String email, String adminPassword) {
        logger.info("AdminServiceImpl : verifyAdmin");
        AdminEntity admin = adminRepository.findByNameAndPassword(email, adminPassword).orElseThrow(() -> new AdminNotFoundException(ResponseMessage.ADMIN_NOT_FOUND));
        if (admin.getIsVerified() == true) {
            return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.ALREADY_VERIFIED, null);
        } else {
            admin.setIsVerified(true);
        }
        adminRepository.save(admin);
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.VERIFIED_SUCCESSFULLY, null);
    }

    @Override
    public Response getAdmin(Long adminId) {
        logger.info("AdminServiceImpl : getAdmin");
        List<ListOfAdminDTO> listOfAdminDTO = new ArrayList<>();
        List<AdminEntity> listOfAdmin = adminRepository.findByAdminId(adminId);
        if (listOfAdmin.size() > 0) {
            listOfAdmin.stream().forEach(adminEntity -> {
                ListOfAdminDTO listOfAdminDTO1 = new ListOfAdminDTO();
                listOfAdminDTO1.setUserRole(adminEntity.getUserRole());
                listOfAdminDTO1.setAdminName(adminEntity.getAdminName());
                listOfAdminDTO1.setAdminEmail(adminEntity.getAdminEmail());
                listOfAdminDTO1.setAdminPhoneNumber(adminEntity.getAdminPhoneNumber());
                listOfAdminDTO1.setIsVerified(adminEntity.getIsVerified());
                listOfAdminDTO1.setCreatedOnDate(adminEntity.getCreatedOnDate());
                listOfAdminDTO1.setUpdatedOnDate(adminEntity.getUpdatedOnDate());
                listOfAdminDTO.add(listOfAdminDTO1);
            });
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCHED_SUCCESSFULLY, listOfAdminDTO);
        } else {
            throw new AdminNotFoundException(ResponseMessage.ADMIN_NOT_FOUND);
        }
    }

    @Override
    public Response forgotPassword(String adminEmail) throws MessagingException {
        logger.info("AdminServiceImpl : forgotPassword");
        AdminEntity existingData = adminRepository.findByEmail(adminEmail).orElseThrow(() -> new AdminNotFoundException(ResponseMessage.NO_ADMIN_HAVING_THIS_EMAIL));
        if (existingData.getAdminEmail().equals(adminEmail)) {
            if (existingData != null) {
                emailService.resetPasswordEmail(existingData);
            }
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.EMAIL_IS_WRONG, null);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.EMAIL_SEND_SUCCESSFULLY, null);
    }

    @Override
    public Response resetPassword(ResetPasswordDTO resetPasswordDTO) {
        logger.info("AdminServiceImpl : resetPassword");
        Response response = validationService.validateResetPasswordAdminReq(resetPasswordDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        AdminEntity existingData = adminRepository.findByNameAndPassword(resetPasswordDTO.getAdminEmail(), resetPasswordDTO.getAdminPassword()).orElseThrow(() -> new AdminNotFoundException(ResponseMessage.ADMIN_NOT_FOUND));
        if (existingData != null) {
            existingData.setAdminPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
            adminRepository.save(existingData);
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.RESET_PASSWORD_SUCCESSFULLY, null);
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.OLD_PASSWORD_IS_NOT_MATCHING, null);
        }
    }


    @Override
    public Response addDeveloper(DeveloperEntityDTO developerEntityDTO) throws MessagingException {
        logger.info("AdminServiceImpl : addDeveloper");
        Response response = validationService.validateAddDeveloperReq(developerEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        Optional<DeveloperEntity> existingData = developerRepository.findByDevEmailAndMobile(developerEntityDTO.getDevEmail(), developerEntityDTO.getDevMobile());
        DeveloperEntity existingDevName = developerRepository.findByDeveloperName(developerEntityDTO.getDevName());
        if (existingDevName != null) {
            return new Response(ResponseMessage.BAD_REQUEST, "Developer name already taken", null);
        }
        AdminEntity admin = adminRepository.findById(Long.valueOf(developerEntityDTO.getAdminId())).orElseThrow(() -> new AdminNotFoundException(ResponseMessage.ADMIN_NOT_FOUND));
        if (!existingData.isPresent()) {
            DeveloperEntity developer = new DeveloperEntity();
            BeanUtils.copyProperties(developerEntityDTO, developer);
            developer.setAdmin(admin);
            developer.setDevPassword(generatePasswordForDeveloper(developerEntityDTO.getDevEmail()));
            developer.setUserRole(TMSConstants.DEFAULT_DEV_ROLE);
            developer.setAddedOn(new Date());
            developer.setIsDelete(false);
            developerRepository.save(developer);
            emailService.loginEmail(developer);
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, null);
        } else {
            throw new EmailAlreadyExistException(ResponseMessage.EMAIL_PHONE_NO_ALREADY_EXIST);
        }
    }

    private String generatePasswordForDeveloper(String devEmail) {
        String password = "";
        String[] devPassword = devEmail.split("@");
        password += devPassword[0].concat("TMS");
        return password;
    }

    @Override
    public Response removeDeveloper(Long devId) {
        logger.info("AdminServiceImpl : removeDeveloper");
        DeveloperEntity existingData = developerRepository.findById(devId).orElseThrow(() -> new DeveloperNotFoundException(ResponseMessage.DEVELOPER_NOT_FOUND));
        if (existingData != null) {
            existingData.setIsDelete(true);
            developerRepository.save(existingData);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.REMOVED_SUCCESSFULLY, null);
    }

    @Override
    public Response getAllDevelopers(Long adminId) {
        logger.info("AdminServiceImpl : getAllDevelopers");
        AdminEntity admin = adminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("Admin not found"));
        List<ListOfDeveloperDTO> listOfDeveloperDTO = new ArrayList<>();
        List<DeveloperEntity> listOfDevelopers = developerRepository.findAllDevelopersByAdminId(adminId);
        if (listOfDevelopers.size() > 0) {
            listOfDevelopers.stream().forEach(developerEntity -> {
                ListOfDeveloperDTO developerDTO1 = new ListOfDeveloperDTO();
                developerDTO1.setDevName(developerEntity.getDevName());
                developerDTO1.setDevContact(developerEntity.getDevMobile());
                developerDTO1.setDevEmail(developerEntity.getDevEmail());
                developerDTO1.setCreatedBy(developerEntity.getAdmin().getAdminName());
                listOfDeveloperDTO.add(developerDTO1);
            });
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCHED_SUCCESSFULLY, listOfDeveloperDTO);
        } else {
            throw new DeveloperNotFoundException("No developers under this admin id");
        }
    }

    @Override
    public Response getDeveloperById(Long devId) {
        logger.info("AdminServiceImpl : getDeveloperById");
        ListOfDeveloperDTO listOfDeveloperDTO = new ListOfDeveloperDTO();
        DeveloperEntity developerEntity = developerRepository.findByDeveloperId(devId);
        if (developerEntity != null) {
            listOfDeveloperDTO.setDevName(developerEntity.getDevName());
            listOfDeveloperDTO.setDevContact(developerEntity.getDevMobile());
            listOfDeveloperDTO.setDevEmail(developerEntity.getDevEmail());
            listOfDeveloperDTO.setCreatedBy(developerEntity.getAdmin().getAdminName());
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCHED_SUCCESSFULLY, listOfDeveloperDTO);
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.DEVELOPER_NOT_FOUND, null);
        }
    }

    @Override
    public Response updateDeveloper(DeveloperEntityDTO developerEntityDTO, Long devId) {
        logger.info("AdminServiceImpl : updateDeveloper");
        Response response = validationService.validateUpdateDeveloperReq(developerEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        DeveloperEntity existingData = developerRepository.findById(devId).orElseThrow(() -> new DeveloperNotFoundException(ResponseMessage.DEVELOPER_NOT_FOUND));
        if (Objects.nonNull(existingData)) {
            developerEntityDTO.setAddedOn(existingData.getAddedOn());
            developerEntityDTO.setUpdatedOn(new Date());
            developerEntityDTO.setUserRole(TMSConstants.DEFAULT_DEV_ROLE);
            BeanUtils.copyProperties(developerEntityDTO, existingData);
            developerRepository.save(existingData);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.UPDATED_SUCCESSFULLY, null);
    }

    @Override
    public Response devForgotPassword(String devEmail) throws MessagingException {
        logger.info("AdminServiceImpl : devForgotPassword");
        DeveloperEntity existingData = developerRepository.findByEmail(devEmail).orElseThrow(() -> new DeveloperNotFoundException(ResponseMessage.DEVELOPER_NOT_FOUND));
        if (existingData != null) {
            emailService.devForgotPasswordEmail(existingData);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.EMAIL_SEND_SUCCESSFULLY, null);
    }
}