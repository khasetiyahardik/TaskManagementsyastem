package com.example.TMS.service;

import com.example.TMS.dto.AssignDeveloperDTO;
import com.example.TMS.dto.Response;
import com.example.TMS.dto.TaskEntityDTO;

public interface TaskService {
    public Response addTask(TaskEntityDTO taskEntityDTO);

    public Response getAllTask(Long projectId);

    public Response getTaskById(Long taskId);

    public Response updateTask(TaskEntityDTO taskEntityDTO, Long taskId);

    public Response assignDeveloper(AssignDeveloperDTO assignDeveloperDTO, Long taskId);

    public Response removeDeveloper(Long taskId);
}