package com.example.TMS.service;

import com.example.TMS.dto.ProjectEntityDTO;
import com.example.TMS.dto.Response;

public interface ProjectService {
    public Response addProject(ProjectEntityDTO projectEntityDTO);

    public Response getProjectById(Long projectId);

    public Response updateProject(ProjectEntityDTO projectEntityDTO, Long projectId);

    public Response deleteProject(Long projectId);
}