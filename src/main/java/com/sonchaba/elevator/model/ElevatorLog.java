package com.sonchaba.elevator.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class ElevatorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int elevatorId;
    private int floor;
    private String state;
    private String direction;
    private Date timestamp;
    private Long userId;
    private int fromFloor;
    private int toFloor;

}
