package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mainCode.FloorSubsystem;
import mainCode.Instruction;

class TestFloorSubsystem {
	FloorSubsystem floor;
	
	@BeforeEach
	void setup() {
		floor = new FloorSubsystem(null, 1);
	}
	
	@Test
	void testFloorNumber() {
		assertEquals(floor.getFloorNumber(), 1);
	}
	
	@Test
	void testReadInput() {
		Instruction inst = floor.readInputFile("inputFile.csv");
		System.out.println(inst.getFloor() + ", " + inst.getFloorBut());
	}
}
