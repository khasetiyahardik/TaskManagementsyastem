package com.example.TMS.service;

import com.example.TMS.dto.Response;
import com.example.TMS.dto.SprintEntityDTO;

public interface SprintService {
    public Response createSprint(SprintEntityDTO sprintEntityDTO);

    public Response getSprintById(Long sprintId);

    public Response addTask(SprintEntityDTO sprintEntityDTO, Long sprintId);

    public Response updateSprintStatus(Long sprintId);
}