package mainCode;
import java.io.*;
import java.util.*;

public class FloorSubsystem implements Runnable {

	private int floorNumber;
	private String[] myArray;
	private Scheduler scheduler;
	Properties prop = new Properties();

	public void run() {

		while (true) {
			synchronized (scheduler) {
				// wait while both the input and output lists are empty
				while (scheduler.acknowledged.isEmpty()) {
					try {
						scheduler.wait();
					} catch (InterruptedException e) {
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
	public FloorSubsystem(Scheduler scheduler) {
		FileInputStream ip;
		try {
			ip = new FileInputStream("config.properties");
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		this.scheduler = scheduler;
		this.floorNumber = Integer.parseInt(prop.getProperty("FLOORS"));
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