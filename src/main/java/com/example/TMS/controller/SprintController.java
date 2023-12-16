package com.example.TMS.controller;

import com.example.TMS.dto.Response;
import com.example.TMS.dto.SprintEntityDTO;
import com.example.TMS.service.SprintService;
import com.example.TMS.util.ResponseMessage;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sprint")
public class SprintController {
    private static final Logger logger = LoggerFactory.getLogger(SprintController.class);
    @Autowired
    SprintService sprintService;

    @PostMapping("/createSprint")
    public Response createSprint(@RequestBody SprintEntityDTO sprintEntityDTO) {
        logger.info("SprintController : createSprint");
        return sprintService.createSprint(sprintEntityDTO);
    }

    @GetMapping("/getSprintById/{sprintId}")
    public Response getSprintById(@PathVariable("sprintId") Long sprintId) {
        logger.info("SprintController : getSprintById");
        return sprintService.getSprintById(sprintId);
    }

    @PostMapping("/addTask/{sprintId}")
    public Response addTask(@RequestBody SprintEntityDTO sprintEntityDTO, @PathVariable("sprintId") Long sprintId) {
        logger.info("SprintController : addTask");
        return sprintService.addTask(sprintEntityDTO, sprintId);
    }

    @PutMapping("/updateSprintStatus/{sprintId}")
    public Response updateSprintStatus(@Valid @PathVariable("sprintId") Long sprintId, Errors errors) {
        logger.info("SprintController : updateSprintStatus");
        if (errors.hasErrors()) {
            return new Response(ResponseMessage.BAD_REQUEST, errors.getAllErrors().get(0).getDefaultMessage(), null);
        } else {
            return sprintService.updateSprintStatus(sprintId);
        }
    }
}