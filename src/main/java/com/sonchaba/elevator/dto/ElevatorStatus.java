package com.sonchaba.elevator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElevatorStatus {
    private int id;
    private int currentFloor;
    private String state;
    private String direction;
}
