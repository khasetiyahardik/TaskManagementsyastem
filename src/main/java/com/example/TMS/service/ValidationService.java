package com.example.TMS.service;

import com.example.TMS.dto.*;

public interface ValidationService {

    Response validateUpdateDeveloperReq(DeveloperEntityDTO developerEntityDTO);

    Response validateAddDeveloperReq(DeveloperEntityDTO developerEntityDTO);

    Response validateRegisterAdminReq(AdminEntityDTO adminEntityDTO);

    Response validateLoginAdminReq(AdminLoginDTO adminLoginDTO);

    Response validateResetPasswordAdminReq(ResetPasswordDTO resetPasswordDTO);

    Response validateUpdateProjectReq(ProjectEntityDTO projectEntityDTO);

    Response validateAddProjectReq(ProjectEntityDTO projectEntityDTO);

    Response validateAddTaskReq(SprintEntityDTO sprintEntityDTO);

    Response validateCreateSprintReq(SprintEntityDTO sprintEntityDTO);

    Response validateUpdateSubTaskReq(SubtaskEntityDTO subtaskEntityDTO);

    Response validateAddSubTaskReq(SubtaskEntityDTO subtaskEntityDTO);

    Response validateAssignDeveloperReq(AssignDeveloperDTO assignDeveloperDTO);

    Response validateUpdateTaskReq(TaskEntityDTO taskEntityDTO);

    Response validateCreateTaskReq(TaskEntityDTO taskEntityDTO);
}
