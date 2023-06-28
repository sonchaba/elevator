package com.sonchaba.elevator.service;

import com.sonchaba.elevator.dto.ElevatorStatus;
import com.sonchaba.elevator.model.Elevator;
import com.sonchaba.elevator.model.ElevatorLog;
import com.sonchaba.elevator.repository.ElevatorLogRepository;
import com.sonchaba.elevator.repository.ElevatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElevatorService {

    private final Logger logger = LoggerFactory.getLogger(ElevatorService.class);

    private final ElevatorLogRepository elevatorLogRepository;
    private final ElevatorRepository elevatorRepository;

    private final Map<Integer, Elevator> elevators = new HashMap<>();
    @Value("${elevator.floors:10}")
    private int totalFloors;

    public void setTotalFloors(int totalFloors) {
        this.totalFloors = totalFloors;
    }

    public void callElevator(int fromFloor, int toFloor) {
        int elevatorId = fromFloor % totalFloors;

        Elevator elevator = elevators.get(elevatorId);
        this.call(elevator, fromFloor, toFloor);

        saveElevatorLog(elevatorId, elevator.getCurrentFloor(), elevator.getState(), elevator.getDirection());
    }


    public List<ElevatorStatus> getElevatorStatus() {
        List<ElevatorStatus> statuses = new ArrayList<>();
        for (Map.Entry<Integer, Elevator> entry : elevators.entrySet()) {
            Elevator elevator = entry.getValue();
            ElevatorStatus status = new ElevatorStatus(entry.getKey(), elevator.getCurrentFloor(),
                    elevator.getState(), elevator.getDirection());
            statuses.add(status);
        }
        return statuses;
    }

    public void call(Elevator elevator, int fromFloor, int toFloor) {
        if (elevator.getCurrentFloor() == fromFloor) {
            elevator.setState("OPENING_DOORS");
            // Simulate doors opening for 2 seconds
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elevator.setState("CLOSING_DOORS");
            // Simulate doors closing for 2 seconds
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (elevator.getCurrentFloor() < fromFloor) {
            elevator.setDirection("UP");
            elevator.setState("MOVING");
            // Simulate elevator moving up to the fromFloor
            while (elevator.getCurrentFloor() < fromFloor) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
            }
        } else if (elevator.getCurrentFloor() > fromFloor) {
            elevator.setDirection("DOWN");
            elevator.setState("MOVING");
            // Simulate elevator moving down to the fromFloor
            while (elevator.getCurrentFloor() > fromFloor) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);

            }
        }

        elevator.setState("OPENING_DOORS");
        // Simulate doors opening for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        elevator.setState("CLOSING_DOORS");
        // Simulate doors closing for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (elevator.getCurrentFloor() < toFloor) {
            elevator.setDirection("UP");
            elevator.setState("MOVING");
            // Simulate elevator moving up to the toFloor
            while (elevator.getCurrentFloor() < toFloor) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);

            }
        } else if (elevator.getCurrentFloor() > toFloor) {
            elevator.setDirection("DOWN");
            elevator.setState("MOVING");
            // Simulate elevator moving down to the toFloor
            while (elevator.getCurrentFloor() > toFloor) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);

            }
        }

        elevator.setState("OPENING_DOORS");
        // Simulate doors opening for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        elevator.setState("CLOSING_DOORS");
        // Simulate doors closing for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elevator.setState("IDLE");
        elevator.setDirection("NONE");
    }

    @Async
    @Scheduled(fixedDelay = 5000)
    public void moveElevators() {
        this.getElevators();
        for (Map.Entry<Integer, Elevator> entry : elevators.entrySet()) {
            Elevator elevator = entry.getValue();
            this.move(elevator);
            log.info("moving elevator {}", elevator);
            saveElevatorLog(entry.getKey(), elevator.getCurrentFloor(), elevator.getState(), elevator.getDirection());
        }
    }

    public void move(Elevator elevator) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
        if (elevator.getCurrentFloor() >= this.totalFloors) {
            elevator.setCurrentFloor(0);
        }
    }


    @Transactional
    private void saveElevatorLog(int elevatorId, int floor, String state, String direction) {
        ElevatorLog log = new ElevatorLog();
        log.setElevatorId(elevatorId);
        log.setFloor(floor);
        log.setState(state);
        log.setDirection(direction);
        log.setTimestamp(new Date());
        elevatorLogRepository.save(log);
    }

    public Map<Integer, Elevator> getElevators() {
        if (elevators.isEmpty()) {
            List<Elevator> elevatorList = elevatorRepository.findAll();
            for (int i = 0; i < elevatorList.size(); i++) {
                elevators.put(i, elevatorList.get(i));
            }
        }
        return elevators;
    }

    public int getTotalFloors() {
        return totalFloors;
    }

    public Elevator getElevator(int elevatorId) {
        return getElevators().get(elevatorId);
    }
}
