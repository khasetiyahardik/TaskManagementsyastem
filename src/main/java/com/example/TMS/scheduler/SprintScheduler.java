package com.example.TMS.scheduler;

import com.example.TMS.entity.SprintEntity;
import com.example.TMS.enums.TmsEnum;
import com.example.TMS.repository.SprintRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SprintScheduler {
    @Autowired
    SprintRepository sprintRepository;

    @Scheduled(cron = "1 * * * * *")
    void sprintExpirationScheduler() {
        log.info("sprint expiration scheduler started: ");
        List<SprintEntity> sprintEntityList = sprintRepository.findAll();
        sprintEntityList.stream().filter(sprint -> sprint.getSprintEndDate().compareTo(new Date()) < 0).forEach(sprint -> sprint.setSprintStatus(TmsEnum.INACTIVE.getValue()));
        sprintRepository.saveAll(sprintEntityList);
        log.info("sprint expiration scheduler ended: ");
    }
}