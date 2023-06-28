package com.sonchaba.elevator.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Elevator {
    @Id
    private int id;
    private int currentFloor;
    private String state;
    private String direction;

    public Elevator() {
    }

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.state = "IDLE";
        this.direction = "NONE";
    }
}
