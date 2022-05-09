package com.cmcglobal.backend.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/v1/schedule")
public interface ScheduleController {

    @PutMapping
    void turnExpiredDotToAvailable();

    @PutMapping("user")
    void syncUserDataFromPOA();
}
