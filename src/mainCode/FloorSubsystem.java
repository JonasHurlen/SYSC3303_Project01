package mainCode;
import java.io.*;
import java.util.*;

public class FloorSubsystem implements Runnable {

	private int floorNumber;
	private String[] myArray;
	private Scheduler scheduler;

	public void run() {

		while (true) {
			synchronized (scheduler) {
				// wait while both the input and output lists are empty
				while (scheduler.acknowledged.isEmpty()) {
					try {
						scheduler.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				System.out.println("Request acknowledged by floor");
				scheduler.acknowledged.pop();
				scheduler.notifyAll();
			}

		}

	}
/**
 * FloorSubsystem Constructor
 * @param scheduler
 * @param floorNumber
 */
	public FloorSubsystem(Scheduler scheduler, int floorNumber) {
		this.scheduler = scheduler;
		this.floorNumber = floorNumber;
	}
	
	/**
	 * readInputFile() method will read in events from csv file to be sent to the scheduler 
	 * @param String that is the name of the csv file
	 * @return Instruction 
	 */

	public Instruction readInputFile(String filename) {
		Instruction instruction = null;
		List<String> inputData = new ArrayList<String>();
		try {
			// Create a new scanner that takes in the csv file
			Scanner scanner = new Scanner(new File(filename));
			scanner.useDelimiter(","); // sets the delimiter pattern
            // Checks if the scanner object has another token, if there is then it will add it to the list
			while (scanner.hasNext()) {
				String x = scanner.next();
				inputData.add(x);
			}
			//Close the Scanner
			scanner.close();
			String[] myArray = inputData.toArray(new String[0]);
            //Creating a new Instruction
			instruction = new Instruction(Integer.parseInt(myArray[1]), Integer.parseInt(myArray[2]));

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			return instruction;
		}
	}

}
