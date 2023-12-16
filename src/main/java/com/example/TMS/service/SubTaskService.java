package com.example.TMS.service;

import com.example.TMS.dto.Response;
import com.example.TMS.dto.SubtaskEntityDTO;

public interface SubTaskService {
    public Response addSubtask(SubtaskEntityDTO subtaskEntityDTO);

    public Response getSubtaskById(Long subTaskId);

    public Response updateSubtask(SubtaskEntityDTO subtaskEntityDTO, Long subTaskId);
}