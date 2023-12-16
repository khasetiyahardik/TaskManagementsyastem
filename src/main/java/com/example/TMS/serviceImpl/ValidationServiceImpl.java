package com.example.TMS.serviceImpl;

import com.example.TMS.dto.*;
import com.example.TMS.service.ValidationService;
import com.example.TMS.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationServiceImpl implements ValidationService {
    private static final Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);

    @Override
    public Response validateUpdateDeveloperReq(DeveloperEntityDTO developerEntityDTO) {
        logger.info("ValidationServiceImpl : validateUpdateDeveloperReq");
        List<String> invalidList = new ArrayList<>();
        if (developerEntityDTO.getDevEmail() == null || developerEntityDTO.getDevEmail().equals("") || developerEntityDTO.getDevEmail().isEmpty()) {
            invalidList.add("Email id is required");
        }
        if (developerEntityDTO.getDevName() == null || developerEntityDTO.getDevName().equals("") || developerEntityDTO.getDevName().isEmpty()) {
            invalidList.add("Name is required");
        }
        if (developerEntityDTO.getDevMobile() == null || developerEntityDTO.getDevMobile().equals("") || developerEntityDTO.getDevMobile().isEmpty()) {
            invalidList.add("Mobile is required");
        }
        if (developerEntityDTO.getAdminId() == null || developerEntityDTO.getAdminId().equals("") || developerEntityDTO.getAdminId().isEmpty()) {
            invalidList.add("Admin id is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateAddDeveloperReq(DeveloperEntityDTO developerEntityDTO) {
        logger.info("ValidationServiceImpl : validateAddDeveloperReq");
        List<String> invalidList = new ArrayList<>();
        if (developerEntityDTO.getDevEmail() == null || developerEntityDTO.getDevEmail().equals("") || developerEntityDTO.getDevEmail().isEmpty()) {
            invalidList.add("Email id is required");
        }
        if (developerEntityDTO.getDevName() == null || developerEntityDTO.getDevName().equals("") || developerEntityDTO.getDevName().isEmpty()) {
            invalidList.add("Name is required");
        }
        if (developerEntityDTO.getDevMobile() == null || developerEntityDTO.getDevMobile().equals("") || developerEntityDTO.getDevMobile().isEmpty()) {
            invalidList.add("Mobile is required");
        }
        if (developerEntityDTO.getAdminId() == null || developerEntityDTO.getAdminId().equals("") || developerEntityDTO.getAdminId().isEmpty()) {
            invalidList.add("Admin id is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateRegisterAdminReq(AdminEntityDTO adminEntityDTO) {
        logger.info("ValidationServiceImpl : validateRegisterAdminReq");
        List<String> invalidList = new ArrayList<>();
        if (adminEntityDTO.getAdminEmail() == null || adminEntityDTO.getAdminEmail().equals("") || adminEntityDTO.getAdminEmail().isEmpty()) {
            invalidList.add("Email id is required");
        }
        if (adminEntityDTO.getAdminName() == null || adminEntityDTO.getAdminName().equals("") || adminEntityDTO.getAdminName().isEmpty()) {
            invalidList.add("Name is required");
        }
        if (adminEntityDTO.getAdminPhoneNumber() == null || adminEntityDTO.getAdminPhoneNumber().equals("") || adminEntityDTO.getAdminPhoneNumber().isEmpty()) {
            invalidList.add("Mobile is required");
        }
        if (adminEntityDTO.getAdminPassword() == null || adminEntityDTO.getAdminPassword().equals("") || adminEntityDTO.getAdminPassword().isEmpty()) {
            invalidList.add("Password is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateLoginAdminReq(AdminLoginDTO adminLoginDTO) {
        logger.info("ValidationServiceImpl : validateLoginAdminReq");
        List<String> invalidList = new ArrayList<>();
        if (adminLoginDTO.getAdminName() == null || adminLoginDTO.getAdminName().equals("") || adminLoginDTO.getAdminName().isEmpty()) {
            invalidList.add("Name is required");
        }
        if (adminLoginDTO.getAdminPassword() == null || adminLoginDTO.getAdminPassword().equals("") || adminLoginDTO.getAdminPassword().isEmpty()) {
            invalidList.add("Password is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateResetPasswordAdminReq(ResetPasswordDTO resetPasswordDTO) {
        logger.info("ValidationServiceImpl : validateResetPasswordAdminReq");
        List<String> invalidList = new ArrayList<>();
        if (resetPasswordDTO.getAdminEmail() == null || resetPasswordDTO.getAdminEmail().equals("") || resetPasswordDTO.getAdminEmail().isEmpty()) {
            invalidList.add("Email id is required");
        }
        if (resetPasswordDTO.getAdminPassword() == null || resetPasswordDTO.getAdminPassword().equals("") || resetPasswordDTO.getAdminPassword().isEmpty()) {
            invalidList.add("Password is required");
        }
        if (resetPasswordDTO.getNewPassword() == null || resetPasswordDTO.getNewPassword().equals("") || resetPasswordDTO.getNewPassword().isEmpty()) {
            invalidList.add("New password is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateUpdateProjectReq(ProjectEntityDTO projectEntityDTO) {
        logger.info("ValidationServiceImpl : validateUpdateProjectReq");
        List<String> invalidList = new ArrayList<>();
        if (projectEntityDTO.getProjectName() == null || projectEntityDTO.getProjectName().equals("") || projectEntityDTO.getProjectName().isEmpty()) {
            invalidList.add("Project name is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateAddProjectReq(ProjectEntityDTO projectEntityDTO) {
        logger.info("ValidationServiceImpl : validateAddProjectReq");
        List<String> invalidList = new ArrayList<>();
        if (projectEntityDTO.getProjectName() == null || projectEntityDTO.getProjectName().equals("") || projectEntityDTO.getProjectName().isEmpty()) {
            invalidList.add("Project name is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateAddTaskReq(SprintEntityDTO sprintEntityDTO) {
        logger.info("ValidationServiceImpl : validateAddTaskReq");
        List<String> invalidList = new ArrayList<>();
        if (sprintEntityDTO.getTaskId() == null || sprintEntityDTO.getTaskId().equals("") || sprintEntityDTO.getTaskId().isEmpty()) {
            invalidList.add("Task id is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);

    }

    @Override
    public Response validateCreateSprintReq(SprintEntityDTO sprintEntityDTO) {
        logger.info("ValidationServiceImpl : validateCreateSprintReq");
        List<String> invalidList = new ArrayList<>();
        if (sprintEntityDTO.getSprintName() == null || sprintEntityDTO.getSprintName().equals("") || sprintEntityDTO.getSprintName().isEmpty()) {
            invalidList.add("Sprint name is required");
        }
        if (sprintEntityDTO.getSprintStartDate() == null || sprintEntityDTO.getSprintStartDate().equals("") || sprintEntityDTO.getSprintStartDate().isEmpty()) {
            invalidList.add("Sprint start date is required");
        }
        if (sprintEntityDTO.getSprintEndDate() == null || sprintEntityDTO.getSprintEndDate().equals("") || sprintEntityDTO.getSprintEndDate().isEmpty()) {
            invalidList.add("Sprint end date is required");
        }
        if (sprintEntityDTO.getTaskId() == null || sprintEntityDTO.getTaskId().equals("") || sprintEntityDTO.getTaskId().isEmpty()) {
            invalidList.add("Task id is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateUpdateSubTaskReq(SubtaskEntityDTO subtaskEntityDTO) {
        logger.info("ValidationServiceImpl : validateUpdateSubTaskReq");
        List<String> invalidList = new ArrayList<>();
        if (subtaskEntityDTO.getSubTaskName() == null || subtaskEntityDTO.getSubTaskName().equals("") || subtaskEntityDTO.getSubTaskName().isEmpty()) {
            invalidList.add("Subtask name is required");
        }
        if (subtaskEntityDTO.getSubTaskStartDate() == null || subtaskEntityDTO.getSubTaskStartDate().equals("") || subtaskEntityDTO.getSubTaskStartDate().isEmpty()) {
            invalidList.add("Subtask start date is required");
        }
        if (subtaskEntityDTO.getSubTaskEndDate() == null || subtaskEntityDTO.getSubTaskEndDate().equals("") || subtaskEntityDTO.getSubTaskEndDate().isEmpty()) {
            invalidList.add("Subtask end date is required");
        }
        if(subtaskEntityDTO.getTaskId() == null || subtaskEntityDTO.getTaskId().equals("") || subtaskEntityDTO.getTaskId().isEmpty()){
            invalidList.add("Task id is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateAddSubTaskReq(SubtaskEntityDTO subtaskEntityDTO) {
        logger.info("ValidationServiceImpl : validateAddSubTaskReq");
        List<String> invalidList = new ArrayList<>();
        if (subtaskEntityDTO.getSubTaskName() == null || subtaskEntityDTO.getSubTaskName().equals("") || subtaskEntityDTO.getSubTaskName().isEmpty()) {
            invalidList.add("Subtask name is required");
        }
        if (subtaskEntityDTO.getSubTaskStartDate() == null || subtaskEntityDTO.getSubTaskStartDate().equals("") || subtaskEntityDTO.getSubTaskStartDate().isEmpty()) {
            invalidList.add("Subtask start date is required");
        }
        if (subtaskEntityDTO.getSubTaskEndDate() == null || subtaskEntityDTO.getSubTaskEndDate().equals("") || subtaskEntityDTO.getSubTaskEndDate().isEmpty()) {
            invalidList.add("Subtask end date is required");
        }
        if(subtaskEntityDTO.getTaskId() == null || subtaskEntityDTO.getTaskId().equals("") || subtaskEntityDTO.getTaskId().isEmpty()){
            invalidList.add("Task id is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateAssignDeveloperReq(AssignDeveloperDTO assignDeveloperDTO) {
        logger.info("ValidationServiceImpl : validateAssignDeveloperReq");
        List<String> invalidList = new ArrayList<>();
        if (assignDeveloperDTO.getDevName() == null || assignDeveloperDTO.getDevName().equals("") || assignDeveloperDTO.getDevName().isEmpty()) {
            invalidList.add("Developer name is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }

    @Override
    public Response validateUpdateTaskReq(TaskEntityDTO taskEntityDTO) {
        logger.info("ValidationServiceImpl : validateUpdateTaskReq");
        List<String> invalidList = new ArrayList<>();
        if (taskEntityDTO.getTaskName() == null || taskEntityDTO.getTaskName().equals("") || taskEntityDTO.getTaskName().isEmpty()) {
            invalidList.add("Task name is required");
        }
        if (taskEntityDTO.getStartDate() == null || taskEntityDTO.getStartDate().equals("") || taskEntityDTO.getStartDate().isEmpty()) {
            invalidList.add("Task start date is required");
        }
        if (taskEntityDTO.getEndDate() == null || taskEntityDTO.getEndDate().equals("") || taskEntityDTO.getEndDate().isEmpty()) {
            invalidList.add("Task end date is required");
        }
        if(taskEntityDTO.getProjectId() == null || taskEntityDTO.getProjectId().equals("") || taskEntityDTO.getProjectId().isEmpty()){
            invalidList.add("Project id is required");
        }
        if(taskEntityDTO.getDevName() == null || taskEntityDTO.getDevName().equals("") || taskEntityDTO.getDevName().isEmpty()){
            invalidList.add("Developer name  is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);

    }

    @Override
    public Response validateCreateTaskReq(TaskEntityDTO taskEntityDTO) {
        logger.info("ValidationServiceImpl : validateCreateTaskReq");
        List<String> invalidList = new ArrayList<>();
        if (taskEntityDTO.getTaskName() == null || taskEntityDTO.getTaskName().equals("") || taskEntityDTO.getTaskName().isEmpty()) {
            invalidList.add("Task name is required");
        }
        if (taskEntityDTO.getStartDate() == null || taskEntityDTO.getStartDate().equals("") || taskEntityDTO.getStartDate().isEmpty()) {
            invalidList.add("Task start date is required");
        }
        if (taskEntityDTO.getEndDate() == null || taskEntityDTO.getEndDate().equals("") || taskEntityDTO.getEndDate().isEmpty()) {
            invalidList.add("Task end date is required");
        }
        if(taskEntityDTO.getProjectId() == null || taskEntityDTO.getProjectId().equals("") || taskEntityDTO.getProjectId().isEmpty()){
            invalidList.add("Project id is required");
        }
        if(taskEntityDTO.getDevName() == null || taskEntityDTO.getDevName().equals("") || taskEntityDTO.getDevName().isEmpty()){
            invalidList.add("Developer name  is required");
        }
        if (invalidList.size() > 0) {
            return new Response(ResponseMessage.BAD_REQUEST, "Bad request", invalidList);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, null, null);
    }
}