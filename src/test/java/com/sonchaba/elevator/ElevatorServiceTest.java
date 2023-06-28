package com.sonchaba.elevator;

import com.sonchaba.elevator.dto.ElevatorStatus;
import com.sonchaba.elevator.model.Elevator;
import com.sonchaba.elevator.model.ElevatorLog;
import com.sonchaba.elevator.repository.ElevatorLogRepository;
import com.sonchaba.elevator.service.ElevatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ElevatorServiceTest {

    @Mock
    private ElevatorLogRepository elevatorLogRepository;

    @InjectMocks
    private ElevatorService elevatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        elevatorService.setTotalFloors(10);

    }

    @Test
    void callElevator_ShouldCallCorrectElevatorAndSaveLog() {
        for (int i = 0; i < elevatorService.getTotalFloors(); i++) {
            elevatorService.getElevators().put(i, new Elevator(i));
        }
        int fromFloor = 3;
        int toFloor = 7;

        Elevator elevator = mock(Elevator.class);
        when(elevator.getCurrentFloor()).thenReturn(fromFloor);
        when(elevator.getState()).thenReturn("IDLE");
        when(elevator.getDirection()).thenReturn("NONE");

        elevatorService.callElevator(fromFloor, toFloor);

        verify(elevatorLogRepository, times(1)).save(any(ElevatorLog.class));
    }

    @Test
    void getElevatorStatus_ShouldReturnCorrectStatuses() {
        Elevator elevator1 = new Elevator(0);
        elevator1.setCurrentFloor(2);
        elevator1.setState("MOVING");
        elevator1.setDirection("UP");

        Elevator elevator2 = new Elevator(1);
        elevator2.setCurrentFloor(5);
        elevator2.setState("IDLE");
        elevator2.setDirection("NONE");

        elevatorService.getElevators().put(0, elevator1);
        elevatorService.getElevators().put(1, elevator2);

        List<ElevatorStatus> expectedStatuses = Arrays.asList(
                new ElevatorStatus(0, 2, "MOVING", "UP"),
                new ElevatorStatus(1, 5, "IDLE", "NONE")
        );

        List<ElevatorStatus> actualStatuses = elevatorService.getElevatorStatus();

        assertEquals(expectedStatuses.size(), actualStatuses.size());
        for (int i = 0; i < expectedStatuses.size(); i++) {
            ElevatorStatus expectedStatus = expectedStatuses.get(i);
            ElevatorStatus actualStatus = actualStatuses.get(i);
            assertEquals(expectedStatus.getId(), actualStatus.getId());
            assertEquals(expectedStatus.getCurrentFloor(), actualStatus.getCurrentFloor());
            assertEquals(expectedStatus.getState(), actualStatus.getState());
            assertEquals(expectedStatus.getDirection(), actualStatus.getDirection());
        }
    }
}
