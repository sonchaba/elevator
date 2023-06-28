package com.sonchaba.elevator.repository;

import com.sonchaba.elevator.model.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElevatorRepository extends JpaRepository<Elevator, Integer> {
}
