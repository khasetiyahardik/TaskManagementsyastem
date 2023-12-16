package com.example.TMS;

import com.example.TMS.dto.ProjectEntityDTO;
import com.example.TMS.entity.ProjectEntity;
import com.example.TMS.repository.ProjectRepository;
import com.example.TMS.service.ProjectService;
import com.example.TMS.util.ResponseMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectControllerTests {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectService projectService;

    @BeforeEach
    public void projectData() {
        ProjectEntity project = new ProjectEntity();
        project.setProjectName("TestingProject");
        projectRepository.save(project);
    }

    @AfterEach
    public void deleteProjectData() {
        projectRepository.deleteAll();
    }

    @Test
    public void testAddProject() {
        ProjectEntityDTO projectEntityDTO = new ProjectEntityDTO();
        projectEntityDTO.setProjectName("project for testing");
        Assertions.assertEquals(ResponseMessage.ADDED_SUCCESSFULLY, projectService.addProject(projectEntityDTO).getMessage());
    }

    @Test
    public void testGetProjectById() {
        Long projectId = projectRepository.findAll().stream().findAny().get().getProjectId();
        Assertions.assertEquals(ResponseMessage.FETCHED_SUCCESSFULLY, projectService.getProjectById(projectId).getMessage());
    }

    @Test
    public void testUpdateProject() {
        Long projectId = projectRepository.findAll().stream().findAny().get().getProjectId();
        ProjectEntityDTO projectEntityDTO = new ProjectEntityDTO();
        projectEntityDTO.setProjectName("P2");
        Assertions.assertEquals(ResponseMessage.UPDATED_SUCCESSFULLY, projectService.updateProject(projectEntityDTO, projectId).getMessage());
    }

    @Test
    public void testRemoveProject() {
        Long projectId = projectRepository.findAll().stream().findAny().get().getProjectId();
        Assertions.assertEquals(ResponseMessage.DELETED_SUCCESSFULLY, projectService.deleteProject(projectId).getMessage());
    }
}