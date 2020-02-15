package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mainCode.ElevatorSubsystem;

class TestElevatorSubsystem {
	ElevatorSubsystem ElevatorSubsystem;
	
	@BeforeEach
	void setup() {
		ElevatorSubsystem = new ElevatorSubsystem(null, 1);
	}
	
	@Test
	void testCarNumber() {
		assertEquals(ElevatorSubsystem.getCarNum(), 1);
	}
	
	@Test
	void testDefaultFloor() {
		assertEquals(ElevatorSubsystem.getFloor(), 0);
	}
	
	@Test
	void testChangeFloor() {
		assertEquals(ElevatorSubsystem.getFloor(), 0);
		
		ElevatorSubsystem.setFloor(3);
		assertEquals(ElevatorSubsystem.getFloor(), 3);
	}
	
	@Test
	void testDefaultButton() {
		assertEquals(ElevatorSubsystem.getButton(), 0);
	}
	
	@Test
	void testChangeButton() {
		assertEquals(ElevatorSubsystem.getButton(), 0);
		
		ElevatorSubsystem.setButton(2);
		assertEquals(ElevatorSubsystem.getButton(), 2);
	}
}
