package com.example.TMS.controller;

import com.example.TMS.dto.Response;
import com.example.TMS.dto.SubtaskEntityDTO;
import com.example.TMS.service.SubTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subtask")
public class SubTaskController {
    private static final Logger logger = LoggerFactory.getLogger(SubTaskController.class);
    @Autowired
    SubTaskService subTaskService;

    @PostMapping("/addSubtask")
    public Response addSubtask(@RequestBody SubtaskEntityDTO subtaskEntityDTO) {
        logger.info("SubTaskController : addSubtask");
        return subTaskService.addSubtask(subtaskEntityDTO);
    }

    @GetMapping("/getSubtaskById/{subTaskId}")
    public Response getSubtaskById(@PathVariable(value = "subTaskId") Long subTaskId) {
        logger.info("SubTaskController : getSubtaskById");
        return subTaskService.getSubtaskById(subTaskId);
    }

    @PutMapping("/updateSubtask/{subTaskId}")
    public Response updateSubtask(@RequestBody SubtaskEntityDTO subtaskEntityDTO, @PathVariable("subTaskId") Long subTaskId) {
        logger.info("SubTaskController : updateSubtask");
        return subTaskService.updateSubtask(subtaskEntityDTO, subTaskId);
    }
}