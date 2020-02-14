package mainCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ElevatorSubsystem implements Runnable {
	

	public Scheduler scheduler;
	private int numCars;//Car number
	//public LinkedList<Instruction> instructions = new LinkedList<Instruction>();
	public Car[] cars;

	public ElevatorSubsystem(Scheduler scheduler, int numCars) {
		this.scheduler = scheduler;
		this.numCars = numCars;
		cars = new Car[numCars];
		for (int i = 0; i < numCars; i++) {
			cars[i] = new Car(i);
			cars[i].setCurrFloor(1);
			cars[i].setDir(0);
		}
	}
	
	public int getCarNum() {
		return this.numCars;
	}
	
	public int readInputFile(String filename) {
		Integer myInt = null;
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

 	         myInt =  Integer.parseInt(myArray[3]);
 	        
        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        	
        	 }
        finally {
        	return myInt;
        }
	}

	public void run() {
		while (true) {
			synchronized (scheduler) {
				//wait while both the input and output lists are empty 
				while (scheduler.outputE.isEmpty() && scheduler.transOut.isEmpty() && !scheduler.inputE.isEmpty()&& !scheduler.transIn.isEmpty()) {
					try {
						scheduler.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//if there is something in the incoming instructions
				if (!scheduler.outputE.isEmpty()) {
					Instruction order = scheduler.outputE.pop();
					//if the instruction is of type 0 return the values from the elevator
					cars[order.getCarNum()].setCurrFloor(cars[order.getCarNum()].getCurrFloor() + order.getMove());
					order.setCarCur(cars[order.getCarNum()].getCurrFloor());
					order.setMove(0);
					System.out.println("Car " + order.getCarNum() + " moved to " + cars[order.getCarNum()].getCurrFloor());
					
					switch(order.getType()) {
						case 0:
							System.out.println("E C0");
							order.setCarPoll(cars);
							scheduler.inputE.add(order);
							break;
						case 1:
							//order.setCarBut(readInputFile("inputFile.csv"));
							System.out.println("E C1");
							order.setCarBut(4);
							scheduler.inputE.add(order);
							break;
						case 2:
							scheduler.transIn.add(order);
							
					

					}
					
				}
				if (!scheduler.transOut.isEmpty()) {
					Instruction order = scheduler.transOut.pop();
					cars[order.getCarNum()].setCurrFloor(cars[order.getCarNum()].getCurrFloor() + order.getMove());
					System.out.println("Car " + order.getCarNum() + " moved to " + cars[order.getCarNum()].getCurrFloor());
					scheduler.transIn.add(order);
				}
				scheduler.notifyAll();
				

			}

		}

	}

	private void Switch(int type) {
		// TODO Auto-generated method stub
		
	}

}