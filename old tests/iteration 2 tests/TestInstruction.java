package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mainCode.Instruction;

class TestInstruction {
	Instruction instruction, instruction2;
	
	@BeforeEach
	void setup() {
		instruction = new Instruction(1, 0);
	}

	@Test
	void testFloor() {
		assertEquals(instruction.getFloor(), 1);
	}
	
	@Test
	void testType() {
		assertEquals(instruction.getType(), 0);
		instruction.setType(1);
		assertEquals(instruction.getType(), 1);
	}
	
	@Test
	void testFloorBut() {
		setupFloorBut();
		assertEquals(instruction.getFloorBut(), 0);
		assertEquals(instruction2.getFloorBut(), 1);
	}
	
	@Test
	void testCarCur() {
		assertEquals(instruction.getCarCur(), 0);
		instruction.setCarCur(4);
		assertEquals(instruction.getCarCur(), 4);
	}
	
	@Test
	void testCarBut() {
		assertEquals(instruction.getCarBut(), 0);
		instruction.setCarBut(2);
		assertEquals(instruction.getCarBut(), 2);
	}
	
	@Test
	void testCarNum() {
		assertEquals(instruction.getCarNum(), 0);
		instruction.setCarNum(5);
		assertEquals(instruction.getCarNum(), 5);
	}
	
	@Test
	void testFloorOrder() {
		assertEquals(instruction.getfloorOrder(), 0);
		instruction.setfloorOrder(3);
		assertEquals(instruction.getfloorOrder(), 3);
	}
	
	@Test
	void testMove() {
		instruction.setMove(1);
		assertEquals(instruction.getMove(), 1);
	}
	
	void setupFloorBut() {
		instruction2 = new Instruction(1, 1);
	}
}
