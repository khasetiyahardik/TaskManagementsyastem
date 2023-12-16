package com.example.TMS.serviceImpl;

import com.example.TMS.dto.ProjectEntityDTO;
import com.example.TMS.dto.Response;
import com.example.TMS.entity.ProjectEntity;
import com.example.TMS.entity.TaskEntity;
import com.example.TMS.exception.ProjectNotFoundException;
import com.example.TMS.repository.ProjectRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.service.ProjectService;
import com.example.TMS.service.ValidationService;
import com.example.TMS.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ValidationService validationService;

    @Override
    public Response addProject(ProjectEntityDTO projectEntityDTO) {
        logger.info("ProjectServiceImpl : addProject");
        Response response = validationService.validateAddProjectReq(projectEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        ProjectEntity projectEntity = new ProjectEntity();
        Optional<ProjectEntity> project = projectRepository.findByProjectName(projectEntityDTO.getProjectName());
        if (!project.isPresent()) {
            projectEntity.setProjectName(projectEntityDTO.getProjectName());
            projectRepository.save(projectEntity);
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, null);
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.PROJECT_ALREADY_EXIST, null);
        }
    }

    @Override
    public Response getProjectById(Long projectId) {
        logger.info("ProjectServiceImpl : getProjectById");
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(ResponseMessage.PROJECT_NOT_FOUND));
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCHED_SUCCESSFULLY, project);
    }

    @Override
    public Response updateProject(ProjectEntityDTO projectEntityDTO, Long projectId) {
        logger.info("ProjectServiceImpl : updateProject");
        Response response = validationService.validateUpdateProjectReq(projectEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        ProjectEntity existingProject = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(ResponseMessage.PROJECT_NOT_FOUND));
        if (existingProject != null) {
            Optional<ProjectEntity> projectEntity = projectRepository.findByProjectName(projectEntityDTO.getProjectName());
            if (projectEntity.isPresent()) {
                if (projectEntityDTO.getProjectName().equalsIgnoreCase(existingProject.getProjectName())) {
                    return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.PROJECT_ALREADY_EXIST, projectEntityDTO.getProjectName());
                }
            }
            existingProject.setProjectName(projectEntityDTO.getProjectName());
            projectRepository.save(existingProject);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.UPDATED_SUCCESSFULLY, null);
    }

    @Override
    public Response deleteProject(Long projectId) {
        logger.info("ProjectServiceImpl : deleteProject");
        ProjectEntity existingProject = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(ResponseMessage.PROJECT_NOT_FOUND));
        if (existingProject != null) {
            projectRepository.deleteById(projectId);
        }
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.DELETED_SUCCESSFULLY, null);
    }
}