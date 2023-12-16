package com.example.TMS.serviceImpl;

import com.example.TMS.dto.AssignDeveloperDTO;
import com.example.TMS.dto.ListOfTaskDTO;
import com.example.TMS.dto.Response;
import com.example.TMS.dto.TaskEntityDTO;
import com.example.TMS.entity.DeveloperEntity;
import com.example.TMS.entity.ProjectEntity;
import com.example.TMS.entity.TaskEntity;
import com.example.TMS.exception.AllotedTimeIsOverException;
import com.example.TMS.exception.DeveloperNotFoundException;
import com.example.TMS.exception.ProjectNotFoundException;
import com.example.TMS.exception.TaskNotFoundException;
import com.example.TMS.repository.DeveloperRepository;
import com.example.TMS.repository.ProjectRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.service.TaskService;
import com.example.TMS.service.ValidationService;
import com.example.TMS.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    DeveloperRepository developerRepository;
    @Autowired
    ValidationService validationService;

    @Override
    public Response addTask(TaskEntityDTO taskEntityDTO) {
        logger.info("TaskServiceImpl : addTask");
        Response response = validationService.validateCreateTaskReq(taskEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        TaskEntity taskEntity = new TaskEntity();
        DeveloperEntity developerEntity = developerRepository.findByDeveloperName(taskEntityDTO.getDevName());
        Optional<TaskEntity> existingData = taskRepository.findByTaskName(taskEntityDTO.getTaskName());
        ProjectEntity project = projectRepository.findById(Long.valueOf(taskEntityDTO.getProjectId())).orElseThrow(() -> new ProjectNotFoundException(ResponseMessage.PROJECT_NOT_FOUND));
        if (existingData.isEmpty()) {
            taskEntity.setTaskType(taskEntityDTO.getTaskType());
            taskEntity.setTaskName(taskEntityDTO.getTaskName());
            taskEntity.setTaskDescription(taskEntityDTO.getTaskDescription());
            taskEntity.setTaskPriority(taskEntityDTO.getTaskPriority());
            taskEntity.setTaskStatus(1l);
            taskEntity.setTaskLabel(taskEntityDTO.getTaskLabel());
            taskEntity.setProjectEntity(project);
            taskEntity.setLoggedHours("0d0h");
            Date start = new Date(Long.valueOf(taskEntityDTO.getStartDate()));
            Date end = new Date(Long.valueOf(taskEntityDTO.getEndDate()));
            taskEntity.setStartDate(start);
            taskEntity.setEndDate(end);
            taskEntity.setEstimatedTimeInDays(countEstimatedTimeInDaysUsingDates(start, end));
            taskEntity.setRemainingTimeInDays(countEstimatedTimeInDaysUsingDates(start, end));
            if (developerEntity == null) {
                throw new DeveloperNotFoundException(ResponseMessage.DEVELOPER_NOT_FOUND);
            }
            taskEntity.setDeveloper(developerEntity);
            taskRepository.save(taskEntity);
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, null);
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.TASK_ALREADY_EXIST, null);
        }
    }

    private String countEstimatedTimeInDaysUsingDates(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        String estimatedTimeInDaysInStringFormat = days + "d0h";
        return estimatedTimeInDaysInStringFormat;
    }


    @Override
    public Response getAllTask(Long projectId) {
        logger.info("TaskServiceImpl : getAllTask");
        List<ListOfTaskDTO> listOfTaskDTO = new ArrayList<>();
        List<TaskEntity> taskEntityList = taskRepository.findByProjectId(projectId);
        if (!taskEntityList.isEmpty()) {
            taskEntityList.stream().forEach(taskEntity -> {
                ListOfTaskDTO listOfTaskDTO1 = new ListOfTaskDTO();
                listOfTaskDTO1.setTaskId(taskEntity.getTaskId());
                listOfTaskDTO1.setTaskName(taskEntity.getTaskName());
                listOfTaskDTO1.setDevName(taskEntity.getDeveloper().getDevName() != null ? taskEntity.getDeveloper().getDevName() : "");
                listOfTaskDTO.add(listOfTaskDTO1);
            });
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCHED_SUCCESSFULLY, listOfTaskDTO);
        } else {
            return new Response(ResponseMessage.NOT_FOUND, ResponseMessage.NO_TASK, projectId);
        }
    }

    @Override
    public Response getTaskById(Long taskId) {
        logger.info("TaskServiceImpl : getTaskById");
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCHED_SUCCESSFULLY, taskEntity);
    }

    @Override
    public Response updateTask(TaskEntityDTO taskEntityDTO, Long taskId) {
        logger.info("TaskServiceImpl : updateTask");
        Response response = validationService.validateUpdateTaskReq(taskEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        ProjectEntity project = projectRepository.findById(Long.valueOf(taskEntityDTO.getProjectId())).orElseThrow(() -> new ProjectNotFoundException(ResponseMessage.PROJECT_NOT_FOUND));
        TaskEntity existingTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(ResponseMessage.TASK_NOT_FOUND));
        if (new Date().equals(existingTask.getEndDate()) || new Date().after(existingTask.getEndDate())) {
            throw new AllotedTimeIsOverException("End date is over");
        }
        DeveloperEntity developerEntity = developerRepository.findByDeveloperName(taskEntityDTO.getDevName());
        if (developerEntity == null) {
            throw new DeveloperNotFoundException(ResponseMessage.DEVELOPER_NOT_FOUND);
        }
        existingTask.setDeveloper(developerEntity);
        existingTask.setTaskName(taskEntityDTO.getTaskName());
        existingTask.setTaskDescription(taskEntityDTO.getTaskDescription());
        existingTask.setTaskPriority(taskEntityDTO.getTaskPriority());
        existingTask.setTaskType(taskEntityDTO.getTaskType());
        existingTask.setTaskStatus(taskEntityDTO.getTaskStatus());
        existingTask.setTaskLabel(taskEntityDTO.getTaskLabel());
        Date start = new Date(Long.valueOf(taskEntityDTO.getStartDate()));
        Date end = new Date(Long.valueOf(taskEntityDTO.getEndDate()));
        existingTask.setLoggedHours(taskEntityDTO.getLoggedHours());
        existingTask.setStartDate(start);
        existingTask.setEndDate(end);
        existingTask.setEstimatedTimeInDays(countEstimatedTimeInDaysUsingDates(start, end));
        existingTask.setRemainingTimeInDays(countRemainingTimeInDays(existingTask.getEstimatedTimeInDays(), taskEntityDTO.getLoggedHours()));
        existingTask.setProjectEntity(project);
        BeanUtils.copyProperties(taskEntityDTO, existingTask);
        taskRepository.save(existingTask);
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.UPDATED_SUCCESSFULLY, null);
    }

    private String countRemainingTimeInDays(String estimatedTimeInDays, String loggedHours) {
        String[] estimatedDays = estimatedTimeInDays.split("d");
        String[] estimatedHours = estimatedDays[1].split("h");
        String[] loggedDays = loggedHours.split("d");
        String[] hours = loggedDays[1].split("h");
        int estimatedDaysInInt = Integer.parseInt(estimatedDays[0]) * 24;
        int estimatedHoursInInt = Integer.parseInt(estimatedHours[0]);
        int totalEstimatedTimeInHours = estimatedDaysInInt + estimatedHoursInInt;
        int loggedDaysInInt = Integer.parseInt(loggedDays[0]) * 24;
        int loggedHoursInInt = Integer.parseInt(hours[0]);
        int totalLoggedHours = loggedDaysInInt + loggedHoursInInt;
        int totalRemainingTimeInHours = totalEstimatedTimeInHours - totalLoggedHours;
        int remainingDays = totalRemainingTimeInHours / 24;
        int remainingHours = totalRemainingTimeInHours % 24;
        String remainingTimeInDays = remainingDays + "d" + remainingHours + "h";
        return remainingTimeInDays;
    }


    @Override
    public Response assignDeveloper(AssignDeveloperDTO assignDeveloperDTO, Long taskId) {
        logger.info("TaskServiceImpl : assignDeveloper");
        Response response = validationService.validateAssignDeveloperReq(assignDeveloperDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(ResponseMessage.TASK_NOT_FOUND));
        DeveloperEntity developerEntity = developerRepository.findByDeveloperName(assignDeveloperDTO.getDevName());
        if (developerEntity != null) {
            taskEntity.setDeveloper(developerEntity);
            BeanUtils.copyProperties(assignDeveloperDTO, taskEntity);
            taskRepository.save(taskEntity);
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.DEVELOPER_ASSIGNED_SUCCESSFULLY, null);
        } else {
            return new Response(ResponseMessage.NOT_FOUND, ResponseMessage.DEVELOPER_NOT_FOUND, null);
        }
    }

    @Override
    public Response removeDeveloper(Long taskId) {
        logger.info("TaskServiceImpl : removeDeveloper");
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(ResponseMessage.TASK_NOT_FOUND));
        taskEntity.setDeveloper(null);
        taskEntity.setTaskStatus(1l);
        taskRepository.save(taskEntity);
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.REMOVED_SUCCESSFULLY, null);
    }
}