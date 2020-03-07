package mainCode;

public class Start {
	public static void main(String[] args) {
		
		Scheduler scheduler = new Scheduler();

		FloorSubsystem myFloor = new FloorSubsystem(scheduler, 2);
		ElevatorSubsystem elevator1 = new ElevatorSubsystem(scheduler);
		
		int numberOfLines = 2;
		Instruction inst;
		
		for (int i = 0; i < numberOfLines; i++) {
			inst = myFloor.readInputFile();
			scheduler.inputF.add(inst);
		}
		//Instruction inst2 = myFloor.readInputFile();
		
		
		//Temp until file to elevator is implemented
		//temp until FloorSub is integrated
		
		//scheduler.inputF.add(inst2);
		//elevator1.button = elevator1.readInputFile("inputFile.csv"); // final destination
		
		Thread tElevator1 = new Thread(elevator1);
		Thread tScheduler = new Thread(scheduler);
		Thread tFloor = new Thread(myFloor);
		
		tScheduler.start();
		tElevator1.start();
		
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