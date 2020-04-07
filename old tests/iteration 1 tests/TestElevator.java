package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mainCode.Elevator;

class TestElevator {
	Elevator elevator;
	
	@BeforeEach
	void setup() {
		elevator = new Elevator(null, 1);
	}
	
	@Test
	void testCarNumber() {
		assertEquals(elevator.getCarNum(), 1);
	}
	
	@Test
	void testDefaultFloor() {
		assertEquals(elevator.getFloor(), 0);
	}
	
	@Test
	void testChangeFloor() {
		assertEquals(elevator.getFloor(), 0);
		
		elevator.setFloor(3);
		assertEquals(elevator.getFloor(), 3);
	}
	
	@Test
	void testDefaultButton() {
		assertEquals(elevator.getButton(), 0);
	}
	
	@Test
	void testChangeButton() {
		assertEquals(elevator.getButton(), 0);
		
		elevator.setButton(2);
		assertEquals(elevator.getButton(), 2);
	}
}
