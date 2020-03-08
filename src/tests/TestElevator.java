package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mainCode.ElevatorSubsystem;

class TestElevator {
	ElevatorSubsystem subsystem;
	
	
	@BeforeEach
	void setup() {
		subsystem = new ElevatorSubsystem();
	}
	
	@Test
	void testCarNumber() {
		
	}
	
	@Test
	void testCarsDefault() {
		assertEquals(subsystem.getCar(0).getId(), 0);
		assertEquals(subsystem.getCarNum(), 1);
	}
}