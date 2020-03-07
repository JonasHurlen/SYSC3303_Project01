package mainCode;

public class Start {
	public static void main(String[] args) {
		
		Scheduler scheduler = new Scheduler();

		FloorSubsystem floors = new FloorSubsystem(scheduler);
		ElevatorSubsystem elevators = new ElevatorSubsystem(scheduler);
		
		int numberOfLines = 5;
		Instruction inst;
		
		for (int i = 0; i < numberOfLines; i++) {
			inst = floors.readInputFile(i);
			scheduler.inputF.add(inst);
		}
		//Instruction inst2 = myFloor.readInputFile();
		
		
		//Temp until file to elevator is implemented
		//temp until FloorSub is integrated
		
		//scheduler.inputF.add(inst2);
		//elevator1.button = elevator1.readInputFile("inputFile.csv"); // final destination
		
		Thread tElevators = new Thread(elevators);
		Thread tScheduler = new Thread(scheduler);
		Thread tFloor = new Thread(floors);
		
		tScheduler.start();
		tElevators.start();
		
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