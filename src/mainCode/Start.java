package mainCode;
//Legacy code!!!!
public class Start {
	public static void main(String[] args) {

		Scheduler scheduler = new Scheduler();
		FloorSubsystem floors = new FloorSubsystem();
		ElevatorSubsystem elevators = new ElevatorSubsystem();

		floors.startReading();

		Thread tElevator = new Thread(elevators);
		Thread tScheduler = new Thread(scheduler);
		Thread tFloor = new Thread(floors);

		tScheduler.start();
		tElevator.start();
		tFloor.start();

	}
}