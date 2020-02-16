package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mainCode.ElevatorSubsystem;

class TestElevator {
	ElevatorSubsystem subsystem;
	
	@BeforeEach
	void setup() {
		subsystem = new ElevatorSubsystem(null, 1);
	}
	
	@Test
	void testCarNumber() {
		assertEquals(subsystem.getCarNum(), 1);
	}
}
