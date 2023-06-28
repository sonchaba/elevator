package com.sonchaba.elevator.config;

import com.sonchaba.elevator.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@EnableAsync
@EnableScheduling
public class ElevatorScheduler {

    @Autowired
    private ElevatorService elevatorService;

    @PostConstruct
    public void init() {
        elevatorService.moveElevators();
    }
}
