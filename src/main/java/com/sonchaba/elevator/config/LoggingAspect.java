package com.sonchaba.elevator.config;

import com.sonchaba.elevator.dto.ElevatorStatus;
import com.sonchaba.elevator.model.ElevatorLog;
import com.sonchaba.elevator.model.User;
import com.sonchaba.elevator.repository.ElevatorLogRepository;
import com.sonchaba.elevator.service.ElevatorService;
import com.sonchaba.elevator.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private ElevatorService elevatorService;

    @Autowired
    private UserService userService;

    @Autowired
    private ElevatorLogRepository elevatorLogRepository;

    public void logElevatorCalls(int fromFloor, int toFloor) {
        User user = userService.getCurrentUser();

        elevatorService.callElevator(fromFloor, toFloor);

        String logMessage = String.format("Elevator called from floor %d to floor %d by %s", fromFloor, toFloor, user.getName());
        logger.info(logMessage);

        saveElevatorLog(user, fromFloor, toFloor);
    }

    private void saveElevatorLog(User user, int fromFloor, int toFloor) {
        ElevatorLog log = new ElevatorLog();
        log.setUserId(user.getId());
        log.setFromFloor(fromFloor);
        log.setToFloor(toFloor);
        log.setTimestamp(new Date());
        elevatorLogRepository.save(log);
    }

    @AfterReturning(pointcut = "execution(* *(..)) && @annotation(org.springframework.web.bind.annotation.GetMapping)", returning = "returnValue")
    public void logElevatorStatus(JoinPoint joinPoint, Object returnValue) {
        List<ElevatorStatus> elevatorStatuses = (List<ElevatorStatus>) returnValue;
        StringBuilder logMessage = new StringBuilder("Elevator statuses:\n");
        for (ElevatorStatus status : elevatorStatuses) {
            logMessage.append(String.format("Elevator %d: Floor %d, State: %s, Direction: %s%n",
                    status.getId(), status.getCurrentFloor(), status.getState(), status.getDirection()));
        }
        logger.info(logMessage.toString());
    }
}
