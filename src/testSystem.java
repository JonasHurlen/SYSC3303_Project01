import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class testSystem {

	private Scheduler myScheduler ;
	private Elevator myElevator1 ;
	private Elevator myElevator2;
	private Elevator myElevator3 ;
	private Elevator myElevator4 ;
	private FloorSubsystem myFloorSubsystem;
	
	@BeforeEach
	void setUp() throws Exception {
		Scheduler myScheduler = new Scheduler();
		Elevator myElevator1 = new Elevator(myScheduler, 1);
		Elevator myElevator2 = new Elevator(myScheduler, 2);
		Elevator myElevator3 = new Elevator(myScheduler, 3);
		Elevator myElevator4 = new Elevator(myScheduler, 4);
		//FloorSubsystem myFloorSubsystem = new FloorSubsystem();
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertEquals(myElevator4.carNum,4);
		assertEquals(myElevator4.button,0);
	}

}
