package com.sonchaba.elevator.repository;

import com.sonchaba.elevator.model.ElevatorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface ElevatorLogRepository extends JpaRepository<ElevatorLog, Long> {
}
