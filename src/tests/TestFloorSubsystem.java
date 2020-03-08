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
		floor = new FloorSubsystem();
	}
	

	
	@Test
	void testReadInput() {
		Instruction inst = floor.readInputFile(0);
		assertEquals(inst.getFloor(), 2);
		assertEquals(inst.getFloorBut(), 1);
		
	}
}
