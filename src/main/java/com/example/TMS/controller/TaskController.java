package com.example.TMS.controller;

import com.example.TMS.dto.AssignDeveloperDTO;
import com.example.TMS.dto.Response;
import com.example.TMS.dto.TaskEntityDTO;
import com.example.TMS.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    TaskService taskService;

    @PostMapping("/addTask")
    public Response addTask(@RequestBody TaskEntityDTO taskEntityDTO) {
        logger.info("TaskController : addTask");
        return taskService.addTask(taskEntityDTO);
    }

    @GetMapping("/getAllTask/{projectId}")
    public Response getAllTask(@PathVariable("projectId") Long projectId) {
        logger.info("TaskController : getAllTask");
        return taskService.getAllTask(projectId);
    }

    @GetMapping("/getTaskById/{taskId}")
    public Response getTaskById(@PathVariable("taskId") Long taskId) {
        logger.info("TaskController : getTaskById");
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/updateTask/{taskId}")
    public Response updateTask(@RequestBody TaskEntityDTO taskEntityDTO, @PathVariable("taskId") Long taskId) {
        logger.info("TaskController : updateTask");
        return taskService.updateTask(taskEntityDTO, taskId);
    }

    @PostMapping("/assignDeveloper/{taskId}")
    public Response assignDeveloper(@RequestBody AssignDeveloperDTO assignDeveloperDTO, @PathVariable("taskId") Long taskId) {
        logger.info("TaskController : assignDeveloper");
        return taskService.assignDeveloper(assignDeveloperDTO, taskId);
    }

    @PostMapping("/removeDeveloper/{taskId}")
    public Response removeDeveloper(@PathVariable("taskId") Long taskId) {
        logger.info("TaskController : removeDeveloper");
        return taskService.removeDeveloper(taskId);
    }
}