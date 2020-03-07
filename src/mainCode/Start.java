package mainCode;

public class Start {
	public static void main(String[] args) {
		
		Scheduler scheduler = new Scheduler();
		FloorSubsystem floors = new FloorSubsystem(scheduler);
		ElevatorSubsystem elevators = new ElevatorSubsystem(scheduler);
		
		floors.startReading();	
		
		Thread tElevator = new Thread(elevators);
		Thread tScheduler = new Thread(scheduler);
		Thread tFloor = new Thread(floors);
		
		tScheduler.start();
		tElevator.start();
		tFloor.start();
		
		
		/*
		List<String> inputData = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(new File("inputFile.csv"));
			scanner.useDelimiter(","); // sets the delimiter pattern
			while (scanner.hasNext()) {
				String x = scanner.next();
				inputData.add(x);
			}
			scanner.close();
			String[] myArray = inputData.toArray(new String[0]);
			for (String s : myArray) {
				System.out.println(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		*/
	}
}