import java.io.*;
import java.util.*;
public class FloorSubsystem implements Runnable{
		
	private String[] myArray;
	
	public void run(){
		
	}
	/**
	 * TimeStamp Method 
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
	
	public void readInputFile(String filename) {
		
		List<String>inputData = new ArrayList<String>();
        try {
        	Scanner scanner = new Scanner(new File(filename));
 	        scanner.useDelimiter(","); //sets the delimiter pattern
 	        
 	        while(scanner.hasNext()) {
 	        	String x=scanner.next();
 	        	inputData.add(x);
 	        }
 	        scanner.close();
 	        String[] myArray = inputData.toArray(new String[0]);  
 	        for (String s: myArray) {
 	        	System.out.println(s);
 	        }
        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        	
        	 }
	}

	
	
	
}
		
	

