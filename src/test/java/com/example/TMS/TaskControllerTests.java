package com.example.TMS;

import com.example.TMS.constant.TMSConstants;
import com.example.TMS.dto.AssignDeveloperDTO;
import com.example.TMS.dto.TaskEntityDTO;
import com.example.TMS.entity.AdminEntity;
import com.example.TMS.entity.DeveloperEntity;
import com.example.TMS.entity.ProjectEntity;
import com.example.TMS.entity.TaskEntity;
import com.example.TMS.repository.AdminRepository;
import com.example.TMS.repository.DeveloperRepository;
import com.example.TMS.repository.ProjectRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.service.TaskService;
import com.example.TMS.util.ResponseMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class TaskControllerTests {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TaskService taskService;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    DeveloperRepository developerRepository;
    @Autowired
    AdminRepository adminRepository;

    @BeforeEach
    public void taskData() {
        ProjectEntity project = new ProjectEntity();
        project.setProjectName("TestingProject");
        projectRepository.save(project);

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName("Task");
        taskEntity.setTaskDescription("Testing task");
        taskEntity.setTaskLabel(1l);
        taskEntity.setTaskStatus(1l);
        taskEntity.setTaskPriority(1l);
        taskEntity.setTaskType(1l);
        Date start = new Date(Long.valueOf("1681018200000"));
        Date end = new Date(Long.valueOf("1681191000000"));
        taskEntity.setStartDate(start);
        taskEntity.setEndDate(end);
        taskEntity.setProjectEntity(project);
        taskRepository.save(taskEntity);

        AdminEntity admin = new AdminEntity();
        admin.setAdminName("dhruman");
        admin.setAdminEmail("dhruman0507@gmail.com");
        admin.setAdminPassword("d123");
        admin.setAdminPhoneNumber("9825846578");
        admin.setUserRole(TMSConstants.DEFAULT_ADMIN_ROLE);
        admin.setIsVerified(false);
        admin.setCreatedOnDate(new Date());
        adminRepository.save(admin);
        AdminEntity existingAdmin = adminRepository.findAll().stream().findAny().get();

        DeveloperEntity developer = new DeveloperEntity();
        developer.setDevName("Dhruv");
        developer.setDevEmail("dhruman1845@gmail.com");
        developer.setDevMobile("9575312458");
        developer.setAddedOn(new Date());
        developer.setUserRole(TMSConstants.DEFAULT_DEV_ROLE);
        developer.setIsDelete(false);
        developer.setAdmin(existingAdmin);
        developerRepository.save(developer);
    }

    @AfterEach
    public void deleteTaskData() {

        taskRepository.deleteAll();
        projectRepository.deleteAll();
        developerRepository.deleteAll();
        adminRepository.deleteAll();

    }

    @Test
    public void testAddTask() {
        Long projectId = projectRepository.findAll().stream().findAny().get().getProjectId();
        String devName = developerRepository.findAll().stream().findAny().get().getDevName();
        TaskEntityDTO taskEntityDTO = new TaskEntityDTO();
        taskEntityDTO.setTaskName("Task 35");
        taskEntityDTO.setTaskDescription("task 35");
        taskEntityDTO.setTaskType(1l);
        taskEntityDTO.setTaskLabel(1l);
        taskEntityDTO.setTaskStatus(1l);
        taskEntityDTO.setTaskPriority(1l);
        taskEntityDTO.setProjectId(String.valueOf(projectId));
        taskEntityDTO.setStartDate("1681018200000");
        taskEntityDTO.setEndDate("1681191000000");
        taskEntityDTO.setDevName(devName);
        Assertions.assertEquals(ResponseMessage.ADDED_SUCCESSFULLY, taskService.addTask(taskEntityDTO).getMessage());
    }

    @Test
    public void testUpdateTask() {
        Long projectId = projectRepository.findAll().stream().findAny().get().getProjectId();
        Long taskId = taskRepository.findAll().stream().findAny().get().getTaskId();
        String devName = developerRepository.findAll().stream().findAny().get().getDevName();
        TaskEntityDTO taskEntityDTO = new TaskEntityDTO();
        taskEntityDTO.setTaskName("Task 1");
        taskEntityDTO.setTaskDescription("task 1");
        taskEntityDTO.setTaskType(1l);
        taskEntityDTO.setTaskLabel(1l);
        taskEntityDTO.setTaskStatus(1l);
        taskEntityDTO.setTaskPriority(1l);
        taskEntityDTO.setStartDate("1681018200000");
        taskEntityDTO.setEndDate("1681191000000");
        taskEntityDTO.setLoggedHours("0d5h");
        taskEntityDTO.setProjectId(String.valueOf(projectId));
        taskEntityDTO.setDevName(devName);
        Assertions.assertEquals(ResponseMessage.UPDATED_SUCCESSFULLY, taskService.updateTask(taskEntityDTO, taskId).getMessage());
    }

    @Test
    public void testAssignDeveloper() {
        Long taskId = taskRepository.findAll().stream().findAny().get().getTaskId();
        AssignDeveloperDTO assignDeveloperDTO = new AssignDeveloperDTO();
        assignDeveloperDTO.setDevName("Dhruv");
        Assertions.assertEquals(ResponseMessage.DEVELOPER_ASSIGNED_SUCCESSFULLY, taskService.assignDeveloper(assignDeveloperDTO, taskId).getMessage());
    }

    @Test
    public void testRemoveDeveloperFromTask() {
        Long taskId = taskRepository.findAll().stream().findAny().get().getTaskId();
        Assertions.assertEquals(ResponseMessage.REMOVED_SUCCESSFULLY, taskService.removeDeveloper(taskId).getMessage());
    }
}
