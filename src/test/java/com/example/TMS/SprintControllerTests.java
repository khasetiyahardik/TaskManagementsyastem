package com.example.TMS;

import com.example.TMS.dto.SprintEntityDTO;
import com.example.TMS.entity.ProjectEntity;
import com.example.TMS.entity.SprintEntity;
import com.example.TMS.entity.TaskEntity;
import com.example.TMS.enums.TmsEnum;
import com.example.TMS.repository.ProjectRepository;
import com.example.TMS.repository.SprintRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.service.SprintService;
import com.example.TMS.util.ResponseMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class SprintControllerTests {
    @Autowired
    SprintRepository sprintRepository;
    @Autowired
    SprintService sprintService;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;

    @BeforeEach
    public void prepareData() {
        SprintEntity sprintEntity = new SprintEntity();
        sprintEntity.setSprintName("Sprint1");
        sprintEntity.setSprintStatus(TmsEnum.INACTIVE.getValue());
        Date start = new Date(Long.valueOf("1680327000000"));
        Date end = new Date(Long.valueOf("1681968600000"));
        sprintEntity.setSprintStartDate(start);
        sprintEntity.setSprintEndDate(end);
        sprintRepository.save(sprintEntity);

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
        Date start1 = new Date(Long.valueOf("1681018200000"));
        Date end1 = new Date(Long.valueOf("1681191000000"));
        taskEntity.setStartDate(start1);
        taskEntity.setEndDate(end1);
        taskEntity.setProjectEntity(project);
        taskRepository.save(taskEntity);
    }

    @AfterEach
    public void deleteData() {
        sprintRepository.deleteAll();
    }

    @Test
    public void testCreateSprint() {
        Long taskId = taskRepository.findAll().stream().findAny().get().getTaskId();
        SprintEntityDTO sprintEntityDTO = new SprintEntityDTO();
        sprintEntityDTO.setSprintName("Test Sprint");
        sprintEntityDTO.setSprintStatus(TmsEnum.INACTIVE.getValue());
        sprintEntityDTO.setSprintStartDate("1680327000000");
        sprintEntityDTO.setSprintEndDate("1681968600000");
        sprintEntityDTO.setTaskId(String.valueOf(taskId));
        Assertions.assertEquals(ResponseMessage.CREATED_SUCCESSFULLY, sprintService.createSprint(sprintEntityDTO).getMessage());
    }

    @Test
    public void testAddTask() {
        Long taskId = taskRepository.findAll().stream().findAny().get().getTaskId();
        Long sprintId = sprintRepository.findAll().stream().findAny().get().getSprintId();
        SprintEntityDTO sprintEntityDTO = new SprintEntityDTO();
        sprintEntityDTO.setTaskId(String.valueOf(taskId));
        Assertions.assertEquals(ResponseMessage.ADDED_SUCCESSFULLY, sprintService.addTask(sprintEntityDTO, sprintId).getMessage());
    }

    @Test
    public void testUpdateSprintStatus() {
        Long sprintId = sprintRepository.findAll().stream().findAny().get().getSprintId();
        Assertions.assertEquals(ResponseMessage.SPRINT_STARTED_SUCCESSFULLY, sprintService.updateSprintStatus(sprintId).getMessage());
    }

    @Test
    public void testGetSprintById() {
        Long sprintId = sprintRepository.findAll().stream().findAny().get().getSprintId();
        Assertions.assertEquals(ResponseMessage.FETCHED_SUCCESSFULLY, sprintService.getSprintById(sprintId).getMessage());
    }
}
