package com.example.TMS;

import com.example.TMS.dto.SubtaskEntityDTO;
import com.example.TMS.entity.ProjectEntity;
import com.example.TMS.entity.SubtaskEntity;
import com.example.TMS.entity.TaskEntity;
import com.example.TMS.repository.ProjectRepository;
import com.example.TMS.repository.SubTaskRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.service.SubTaskService;
import com.example.TMS.util.ResponseMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class SubTaskControllerTests {
    @Autowired
    SubTaskService subTaskService;
    @Autowired
    SubTaskRepository subTaskRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;
    @BeforeEach
    public void subTaskData() {
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

        SubtaskEntity subtaskEntity = new SubtaskEntity();
        subtaskEntity.setSubTaskName("SubTask");
        subtaskEntity.setSubTaskDescription("Created SubTask");
        subtaskEntity.setSubTaskPriority(1l);
        subtaskEntity.setSubTaskStatus(1l);
        Date startDate = new Date(Long.valueOf("1681018200000"));
        Date endDate = new Date(Long.valueOf("1681191000000"));
        subtaskEntity.setSubTaskStartDate(startDate);
        subtaskEntity.setSubTaskEndDate(endDate);
        subTaskRepository.save(subtaskEntity);


    }

    @AfterEach
    public void deleteSubTaskData() {
        subTaskRepository.deleteAll();
    }

    @Test
    public void testAddSubTask() {
        Long taskId = taskRepository.findAll().stream().findAny().get().getTaskId();
        SubtaskEntityDTO subtaskEntityDTO = new SubtaskEntityDTO();
        subtaskEntityDTO.setSubTaskName("Subtask testing by dhruman");
        subtaskEntityDTO.setSubTaskDescription("testing by dhruman");
        subtaskEntityDTO.setSubTaskPriority(1l);
        subtaskEntityDTO.setSubTaskStatus(1l);
        subtaskEntityDTO.setSubTaskStartDate("1681018200000");
        subtaskEntityDTO.setSubTaskEndDate("1681191000000");
        subtaskEntityDTO.setTaskId(String.valueOf(taskId));
        Assertions.assertEquals(ResponseMessage.ADDED_SUCCESSFULLY, subTaskService.addSubtask(subtaskEntityDTO).getMessage());
    }

    @Test
    public void testUpdateSubTask() {
        Long taskId = taskRepository.findAll().stream().findAny().get().getTaskId();
        Long subTaskId = subTaskRepository.findAll().stream().findAny().get().getSubTaskId();
        SubtaskEntityDTO subtaskEntityDTO = new SubtaskEntityDTO();
        subtaskEntityDTO.setSubTaskName("Subtask testing by dhruman");
        subtaskEntityDTO.setSubTaskDescription("testing by dhruman");
        subtaskEntityDTO.setSubTaskPriority(1l);
        subtaskEntityDTO.setSubTaskStatus(1l);
        subtaskEntityDTO.setSubTaskLoggedHours("0d5h");
        subtaskEntityDTO.setSubTaskStartDate("1681018200000");
        subtaskEntityDTO.setSubTaskEndDate("1681191000000");
        subtaskEntityDTO.setTaskId(String.valueOf(taskId));
        Assertions.assertEquals(ResponseMessage.UPDATED_SUCCESSFULLY, subTaskService.updateSubtask(subtaskEntityDTO, subTaskId).getMessage());
    }

    @Test
    public void testGetSubTaskById() {
        Long subTaskId = subTaskRepository.findAll().stream().findAny().get().getSubTaskId();
        Assertions.assertEquals(ResponseMessage.FETCHED_SUCCESSFULLY, subTaskService.getSubtaskById(subTaskId).getMessage());
    }
}
