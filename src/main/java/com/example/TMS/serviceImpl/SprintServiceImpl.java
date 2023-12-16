package com.example.TMS.serviceImpl;

import com.example.TMS.dto.Response;
import com.example.TMS.dto.SprintEntityDTO;
import com.example.TMS.entity.SprintEntity;
import com.example.TMS.entity.TaskEntity;
import com.example.TMS.enums.TmsEnum;
import com.example.TMS.exception.SprintNotFoundException;
import com.example.TMS.exception.TaskNotFoundException;
import com.example.TMS.repository.SprintRepository;
import com.example.TMS.repository.TaskRepository;
import com.example.TMS.service.SprintService;
import com.example.TMS.service.ValidationService;
import com.example.TMS.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SprintServiceImpl implements SprintService {
    private static final Logger logger = LoggerFactory.getLogger(SprintServiceImpl.class);
    @Autowired
    SprintRepository sprintRepository;

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ValidationService validationService;

    @Override
    public Response createSprint(SprintEntityDTO sprintEntityDTO) {
        logger.info("SprintServiceImpl : createSprint");
        Response response = validationService.validateCreateSprintReq(sprintEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        SprintEntity sprintEntity = new SprintEntity();
        TaskEntity taskEntity = taskRepository.findById(Long.valueOf(sprintEntityDTO.getTaskId())).orElseThrow(() -> new TaskNotFoundException(ResponseMessage.TASK_NOT_FOUND));
        Long activeSprintCount = sprintRepository.findActiveSprint();
        if (activeSprintCount == 0) {
            SprintEntity existingSprint = sprintRepository.findBySprintName(sprintEntityDTO.getSprintName());
            if (existingSprint == null) {
                sprintEntity.setSprintName(sprintEntityDTO.getSprintName());
                sprintEntity.setSprintStatus(TmsEnum.INACTIVE.getValue());
                Date start = new Date(Long.valueOf(sprintEntityDTO.getSprintStartDate()));
                Date end = new Date(Long.valueOf(sprintEntityDTO.getSprintEndDate()));
                sprintEntity.setSprintStartDate(start);
                sprintEntity.setSprintEndDate(end);
                sprintEntity.setDurationInDays(countDurationInDays(start, end));
                sprintRepository.save(sprintEntity);
                return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.CREATED_SUCCESSFULLY, null);
            } else {
                return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.SPRINT_ALREADY_AVAILABLE, sprintEntityDTO.getSprintName());
            }
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, ResponseMessage.SPRINT_ALREADY_ACTIVE, null);
        }
    }

    private Long countDurationInDays(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return days;
    }

    @Override
    public Response getSprintById(Long sprintId) {
        logger.info("SprintServiceImpl : getSprintById");
        SprintEntity sprintEntity = sprintRepository.findById(sprintId).orElseThrow(() -> new SprintNotFoundException(ResponseMessage.SPRINT_NOT_FOUND));
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.FETCHED_SUCCESSFULLY, sprintEntity);
    }

    @Override
    public Response addTask(SprintEntityDTO sprintEntityDTO, Long sprintId) {
        logger.info("SprintServiceImpl : addTask");
        Response response = validationService.validateAddTaskReq(sprintEntityDTO);
        if (response.getStatus().equalsIgnoreCase(ResponseMessage.BAD_REQUEST)) {
            return response;
        }
        SprintEntity sprintEntity = sprintRepository.findById(sprintId).orElseThrow(() -> new SprintNotFoundException(ResponseMessage.SPRINT_NOT_FOUND));
        TaskEntity taskEntity = taskRepository.findById(Long.valueOf(sprintEntityDTO.getTaskId())).orElseThrow(() -> new TaskNotFoundException(ResponseMessage.TASK_NOT_FOUND));
        if (taskEntity.getStartDate().after(sprintEntity.getSprintStartDate()) && taskEntity.getEndDate().before(sprintEntity.getSprintEndDate())) {
            sprintEntity.getTaskEntity().add(taskEntity);
        } else {
            return new Response(ResponseMessage.BAD_REQUEST, "Enter start date and end date in duration of sprint", null);
        }
        sprintRepository.save(sprintEntity);
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, null);
    }

    @Override
    public Response updateSprintStatus(Long sprintId) {
        logger.info("SprintServiceImpl : updateSprintStatus");
        SprintEntity sprintEntity = sprintRepository.findById(sprintId).orElseThrow(() -> new SprintNotFoundException(ResponseMessage.SPRINT_NOT_FOUND));
        sprintEntity.setSprintStatus(TmsEnum.ACTIVE.getValue());
        sprintRepository.save(sprintEntity);
        return new Response(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.SPRINT_STARTED_SUCCESSFULLY, null);
    }
}