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
	
	public int getFloorNumber() {
		return this.floorNumber;
	}
	
	/**
	 * readInputFile() method will read in events from csv file to be sent to the scheduler 
	 * @param String that is the name of the csv file
	 * @return Instruction 
	 */

	
	
	public static Instruction readInputFile() {
		//Integer myInt = null;
		List<String>inputData = new ArrayList<String>();
		FileReader input = null;
		int lineNumber = 0;
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
        		instruction = new Instruction(Integer.parseInt(info[1]), Integer.parseInt(info[2]), lineNumber);
        		lineNumber++;
        }
        	
        	
        	
      }
        	catch(IOException e ) {
        		e.printStackTrace();
        	}
       
        
      return instruction;
	}	

}