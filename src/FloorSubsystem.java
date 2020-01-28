import java.io.*;
import java.util.*;
public class FloorSubsystem implements Runnable{
		
	private void run(){
		
	}
	
	public static void main(String[] args) {	
		List<String>inputData = new ArrayList<String>();
	        try {
	        	Scanner scanner = new Scanner(new File("C:/Users/abdel/Onedrive/Desktop/inputFile.csv"));
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
		
	

