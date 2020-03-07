package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mainCode.ElevatorSubsystem;

class TestElevator {
	ElevatorSubsystem subsystem;
	
	
	@BeforeEach
	void setup() {
		subsystem = new ElevatorSubsystem(null);
	}
	
	@Test
	void testCarNumber() {
		assertEquals(subsystem.getCarNum(), 1);
	}
	
	@Test
	void testCarsDefault() {
		assertEquals(subsystem.getCar(0).getId(), 0);
	}
}