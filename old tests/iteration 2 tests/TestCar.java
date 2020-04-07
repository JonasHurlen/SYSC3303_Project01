package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mainCode.Car;

class TestCar {
	Car car;
	
	@BeforeEach
	void setup() {
		car = new Car(1);
	}
	
	@Test
	void testCarID() {
		assertEquals(car.getId(), 1);
	}
	
	@Test
	void testCarDirection() {
		car.setDir(0);
		assertEquals(car.getDir(), 0);
		
		car.setDir(1);
		assertEquals(car.getDir(), 1);
	}
	
	@Test
	void testCurFloor() {
		for (int i=0; i<5; i++) {
			car.setCurrFloor(i);
			assertEquals(car.getCurrFloor(), i);
		}
	}
}
