package com.example.TMS.serviceImpl;

import com.example.TMS.dto.Response;
import com.example.TMS.dto.SubtaskEntityDTO;
import com.example.TMS.entity.SubtaskEntity;
import com.example.TMS.entity.TaskEntity;
import com.example.TMS.exception.TaskNotFoundException;
import com.example.TMS.repository.SubTaskRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.service.SubTaskService;
import com.example.TMS.service.ValidationService;
import com.example.TMS.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class SubTaskServiceImpl implements SubTaskService {
    private static final Logger logger = LoggerFactory.getLogger(SubTaskServiceImpl.class);
    @Autowired
    SubTaskRepository subTaskRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ValidationService validationService;

    @Override
    public Response addSubtask(SubtaskEntityDTO subtaskEntityDTO) {
        logger.info("SubTaskServiceImpl : addSubtask");
        Response response = validationService.validateAddSubTaskReq(subtaskEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        SubtaskEntity subtaskEntity = new SubtaskEntity();
        TaskEntity taskEntity = taskRepository.findById(Long.valueOf(subtaskEntityDTO.getTaskId())).orElseThrow(() -> new TaskNotFoundException(ResponseMessage.TASK_NOT_FOUND));
        Optional<SubtaskEntity> existingSubtask = subTaskRepository.findBySubtaskName(subtaskEntityDTO.getSubTaskName());
        if (!existingSubtask.isPresent()) {
            subtaskEntity.setSubTaskName(subtaskEntityDTO.getSubTaskName());
            subtaskEntity.setSubTaskDescription(subtaskEntityDTO.getSubTaskDescription());
            subtaskEntity.setSubTaskPriority(subtaskEntityDTO.getSubTaskPriority());
            subtaskEntity.setSubTaskStatus(subtaskEntityDTO.getSubTaskStatus());
            subtaskEntity.setSubTaskLoggedHours("0d0h");
            Date start = new Date(Long.valueOf(subtaskEntityDTO.getSubTaskStartDate()));
            Date end = new Date(Long.valueOf(subtaskEntityDTO.getSubTaskEndDate()));
            if (start.compareTo(taskEntity.getStartDate()) >= 0) {
                subtaskEntity.setSubTaskStartDate(start);
            } else {
                return new Response(ResponseMessage.BAD_REQUEST, "Enter date equals or after the task's starting date" + "Task's starting date is: ", taskEntity.getStartDate());
            }
            if (end.compareTo(taskEntity.getEndDate()) <= 0 && end.compareTo(start) >= 0) {
                subtaskEntity.setSubTaskEndDate(end);
            } else {
                return new Response(ResponseMessage.BAD_REQUEST, "Enter date equals or before the task's ending date" + "Task's ending date is: ", taskEntity.getEndDate());
            }
            subtaskEntity.setSubTaskEstimatedTimeInDays(countEstimatedTimeInDaysUsingDates(start, end));
            subtaskEntity.setSubTaskRemainingTimeInDays(countEstimatedTimeInDaysUsingDates(start, end));
            subTaskRepository.save(subtaskEntity);
            return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, null);
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.SUBTASK_ALREADY_EXIST, null);
        }
    }

    private String countEstimatedTimeInDaysUsingDates(Date subTaskStartDate, Date subTaskEndDate) {
        long diff = subTaskEndDate.getTime() - subTaskStartDate.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        String estimatedTimeInDaysInStringFormat = days + "d0h";
        return String.valueOf(estimatedTimeInDaysInStringFormat);
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
    public Response getSubtaskById(Long subTaskId) {
        logger.info("SubTaskServiceImpl : getSubtaskById");
        SubtaskEntity subtaskEntity = subTaskRepository.findById(subTaskId).orElseThrow(() -> new TaskNotFoundException("Subtask not found"));
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCHED_SUCCESSFULLY, subtaskEntity);
    }

    @Override
    @Transactional
    public Response updateSubtask(SubtaskEntityDTO subtaskEntityDTO, Long subTaskId) {
        logger.info("SubTaskServiceImpl : updateSubtask");
        Response response = validationService.validateUpdateSubTaskReq(subtaskEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        SubtaskEntity subtaskEntity = subTaskRepository.findById(subTaskId).orElseThrow(() -> new TaskNotFoundException(ResponseMessage.TASK_NOT_FOUND));
        TaskEntity taskEntity = taskRepository.findById(Long.valueOf(subtaskEntityDTO.getTaskId())).orElseThrow(() -> new TaskNotFoundException(ResponseMessage.TASK_NOT_FOUND));
        subtaskEntity.setSubTaskName(subtaskEntityDTO.getSubTaskName());
        subtaskEntity.setSubTaskDescription(subtaskEntityDTO.getSubTaskDescription());
        subtaskEntity.setSubTaskPriority(subtaskEntityDTO.getSubTaskPriority());
        subtaskEntity.setSubTaskStatus(subtaskEntityDTO.getSubTaskStatus());
        subtaskEntity.setSubTaskLoggedHours(subtaskEntityDTO.getSubTaskLoggedHours());
        Date start = new Date(Long.valueOf(subtaskEntityDTO.getSubTaskStartDate()));
        Date end = new Date(Long.valueOf(subtaskEntityDTO.getSubTaskEndDate()));
        if (start.compareTo(taskEntity.getStartDate()) >= 0) {
            subtaskEntity.setSubTaskStartDate(start);
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, "Enter date equals or after the task's starting date" + "Task's starting date is: ", taskEntity.getStartDate());
        }
        if (end.compareTo(taskEntity.getEndDate()) <= 0 && end.compareTo(start) >= 0) {
            subtaskEntity.setSubTaskEndDate(end);
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, "Enter date equals or before the task's ending date" + "Task's ending date is: ", taskEntity.getEndDate());
        }
        subtaskEntity.setSubTaskEstimatedTimeInDays(countEstimatedTimeInDaysUsingDates(start, end));
        subtaskEntity.setSubTaskRemainingTimeInDays(countRemainingTimeInDays(subtaskEntity.getSubTaskEstimatedTimeInDays(), subtaskEntityDTO.getSubTaskLoggedHours()));
        subTaskRepository.save(subtaskEntity);
        taskEntity.getSubtaskEntitiesId().add(subtaskEntity.getSubTaskId());
        taskRepository.save(taskEntity);
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.UPDATED_SUCCESSFULLY, null);
    }
}