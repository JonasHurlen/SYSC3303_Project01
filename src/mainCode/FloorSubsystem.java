package mainCode;
import java.io.*;
import java.util.*;

public class FloorSubsystem implements Runnable {

	private int floorNumber;
	private String[] myArray;
	private Scheduler scheduler;
	
	/**
	 * Runs the floor subsystem
	 *
	 * @throws InterruptedException if thread is inaccessible
	 */
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
	 * Public constructor for class floor subsystem
	 *
	 * @param scheduler scheduler used to schedule the relevant elevators
	 * @param floorNumber floor number
	 */
	public FloorSubsystem(Scheduler scheduler, int floorNumber) {
		this.scheduler = scheduler;
		this.floorNumber = floorNumber;
	}
	
	/**
	 * Gets the floor number of the elevator
	 *
	 * @return the floor number
	 */
	public int getFloorNumber() {
		return this.floorNumber;
	}
	
	/**
	 * Reads in events from .txt file to be sent to the scheduler 
	 *
	 * @return instructions obtained from .txt file
	 * @throws FileNotFoundException if the .txt file cannot be found
	 * @throws IOException if the input data cannot be read from
	 */
	public static Instruction readInputFile() {
		//Integer myInt = null;
		List<String>inputData = new ArrayList<String>();
		FileReader input = null;
        try {
         input = new FileReader("inputFile.txt");
 	        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        	
        	 }
        BufferedReader buff=new BufferedReader(input);
        String myLine =null;
        Instruction instruction = null;
        
      try {
        	while((myLine = buff.readLine()) !=null){
        		String[] info = myLine.split(" ");
        		String destinationFloor = info[3]; 
        		inputData.add(destinationFloor);
        		//System.out.println((Integer.parseInt(x.get(0))));
        		instruction = new Instruction(Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        }
        	
        	
        	
      }
        	catch(IOException e ) {
        		e.printStackTrace();
        	}
       
        
      return instruction;
	}	

}
