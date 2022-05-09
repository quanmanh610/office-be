package com.cmcglobal.backend.controller.impl;

import com.cmcglobal.backend.controller.ScheduleController;
import com.cmcglobal.backend.entity.UserFlattened;
import com.cmcglobal.backend.repository.DotRepository;
import com.cmcglobal.backend.repository.UserFlattenedRepository;
import com.cmcglobal.backend.service.PoaService;
import jodd.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@Slf4j
public class ScheduleControllerImpl implements ScheduleController {
    @Autowired
    private DotRepository dotRepository;

    @Autowired
    private PoaService poaService;

    /**
     * Check at midnight every day, set expired dots to available
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void turnExpiredDotToAvailable() {
        List<Integer> dotIds = dotRepository.findAllByToDateAfter(LocalDate.now());
        dotRepository.turnExpiredDotToAvailable(dotIds);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void syncUserDataFromPOA() {
        poaService.syncUserData();
    }
}
