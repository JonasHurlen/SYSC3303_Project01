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

	public FloorSubsystem(Scheduler scheduler, int floorNumber) {
		this.scheduler = scheduler;
		this.floorNumber = floorNumber;
	}

	/**
	 * TimeStamp Method
	 * 
	 * @return Returns the at which the elevator what called/pressed
	 */
	private String TimeStamp() {
		return myArray[0];
	}

	/**
	 * 
	 * @returns Floor number
	 */
	private String FloorNumber() {
		return myArray[1];
	}

	private String UpOrDown() {
		return myArray[2];
	}

	private String ButtonPressed() {
		return myArray[3];
	}

	/**
	 * 
	 * @param filename
	 */

	public Instruction readInputFile(String filename) {
		Instruction instruction = null;
		List<String> inputData = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(new File(filename));
			scanner.useDelimiter(","); // sets the delimiter pattern

			while (scanner.hasNext()) {
				String x = scanner.next();
				inputData.add(x);
			}
			scanner.close();
			String[] myArray = inputData.toArray(new String[0]);

			instruction = new Instruction(Integer.parseInt(myArray[1]), Integer.parseInt(myArray[2]));

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			return instruction;
		}
	}

}
