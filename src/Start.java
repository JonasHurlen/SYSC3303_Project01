
public class Start {
	public static void main(String[] args) {
		
		Scheduler scheduler = new Scheduler();

		FloorSubsystem myFloor = new FloorSubsystem();
		Instruction fromFloor = myFloor.readInputFile("inputFile.csv");
		
		
		Elevator elevator1 = new Elevator(scheduler,1);
		//Temp until file to elevator is implemented

		elevator1.currFloor = 8; // where the elevator is located
		//temp until FloorSub is integrated
		scheduler.inputF.add(fromFloor);
		elevator1.button = elevator1.readInputFile("inputFile.csv"); // final destination
		
		Thread tElevator1 = new Thread(elevator1);
		Thread tScheduler = new Thread(scheduler);
		
		
		tElevator1.start();
		tScheduler.start();
		
		
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
