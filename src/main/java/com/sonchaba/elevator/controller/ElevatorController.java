package com.sonchaba.elevator.controller;

import com.sonchaba.elevator.model.Elevator;
import com.sonchaba.elevator.service.ElevatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ElevatorController {
    private final ElevatorService elevatorService;

    @PostMapping("/call")
    public void callElevator(@RequestParam int fromFloor, @RequestParam int toFloor) {
         elevatorService.callElevator(fromFloor, toFloor);
    }

    @GetMapping("/state/{elevatorId}")
    public Elevator getElevatorState(@PathVariable int elevatorId) {
        return elevatorService.getElevator(elevatorId);
    }
}
