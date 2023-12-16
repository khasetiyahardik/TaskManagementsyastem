package com.example.TMS.controller;

import com.example.TMS.dto.ProjectEntityDTO;
import com.example.TMS.dto.Response;
import com.example.TMS.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    ProjectService projectService;

    @PostMapping("/addProject")
    public Response addProject(@RequestBody ProjectEntityDTO projectEntityDTO) {
        logger.info("ProjectController : addProject");
        return projectService.addProject(projectEntityDTO);
    }

    @GetMapping("/getProjectById/{projectId}")
    public Response getProjectById(@PathVariable("projectId") Long projectId) {
        logger.info("ProjectController : getProjectById");
        return projectService.getProjectById(projectId);
    }

    @PutMapping("/updateProject/{projectId}")
    public Response updateProject(@RequestBody ProjectEntityDTO projectEntityDTO, @PathVariable("projectId") Long projectId) {
        logger.info("ProjectController : updateProject");
        return projectService.updateProject(projectEntityDTO, projectId);
    }

    @DeleteMapping("/deleteProject/{projectId}")
    public Response deleteProject(@PathVariable("projectId") Long projectId) {
        logger.info("ProjectController : deleteProject");
        return projectService.deleteProject(projectId);
    }
}