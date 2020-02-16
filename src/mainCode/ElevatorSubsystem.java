package mainCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ElevatorSubsystem implements Runnable {
	

	public Scheduler scheduler;
	private int numCars;//Car number
	public Car[] cars;
	private ElevatorState state;

	public ElevatorSubsystem(Scheduler scheduler, int numCars) {
		this.scheduler = scheduler;
		this.numCars = numCars;
		cars = new Car[numCars];
		for (int i = 0; i < numCars; i++) {
			cars[i] = new Car(i);
			cars[i].setCurrFloor(4);
			cars[i].setDir(0);
		}
		state = ElevatorState.WAITING;
	}
	
	public int getCarNum() {
		return this.numCars;
	}
	
	/**
	 * @param carID ID from 0 to carNum-1
	 * @return
	 */
	public Car getCar(int carID) {
		if (carID < 0 || carID >= numCars) throw new IndexOutOfBoundsException();
		
		return this.cars[carID];
	}

	public List<String> readInputFile(String filename) {
		Integer myInt = null;
		List<String>inputData = new ArrayList<String>();
		//String[] myArray = null;
		//int[] arr= null;
        try {
        	Scanner scanner = new Scanner(new File(filename)); // for parsing through the lines
 	        scanner.useDelimiter(","); //sets the delimiter pattern
 	   
 	        while(scanner.hasNext()) {
 	        	String x=scanner.next();
 	        	inputData.add(x);
 	        	Scanner lines = new Scanner(new File(filename)); // to get each line
 	        	while (lines.hasNext()) {
 	        		String y = scanner.next();
 	        		inputData.add(y);
 	        		
 	        	}
 	        }
 	        scanner.close();
        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        	
        	 }
        finally {
        	return inputData ;
        }
	}
	public void run() {
		while (true) {
			synchronized (scheduler) {
				//wait while both the input and output lists are empty 
				while (scheduler.outputE.isEmpty()&& !scheduler.inputE.isEmpty()) {
					try {
						state = ElevatorState.BLOCKED;
						scheduler.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//if there is something in the incoming instructions
				if (!scheduler.outputE.isEmpty()) {
					state = ElevatorState.BUSY;
					Instruction order = scheduler.outputE.pop();
					
					//moves the elevator
					if(order.getMove()!=0) {
						cars[order.getCarNum()].setCurrFloor(cars[order.getCarNum()].getCurrFloor() + (order.getMove()));
						order.setCarCur(cars[order.getCarNum()].getCurrFloor());
						order.setMove(0);
						System.out.println("Car " + order.getCarNum() + " moved to " + cars[order.getCarNum()].getCurrFloor());
					}
					
					/*
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					*/
					switch(order.getType()) {
						case 0:
							scheduler.console("E C0");
							//System.out.println("E C0");
							order.setCarPoll(cars);
							scheduler.inputE.add(order);
							break;
						case 1:
							//
							//order.setCarBut(readInputFile("inputFile.csv"));
							//	
							
							List<String> x = readInputFile("C:\\Users\\abdel\\Onedrive\\Desktop\\inputFile.csv");
							String elementone = x.get(3);
							String elementtwo = x.get(7);
							String elementthree = x.get(11);
							
							//Change String to ints 
							
							int p = Integer.parseInt(elementone);
							int y = Integer.parseInt(elementtwo);
							int z = Integer.parseInt(elementthree);
							int[] myarray = {p,y,z};
							
							for ( int i=0; i<3; i++) {
								//
								order.setCarBut(myarray[i]);
								scheduler.console("E C1");
								scheduler.console("Doors open, someone gets on");
								
								//System.out.println("E C1");
								//System.out.println("Doors open, someone gets on");
								//order.setCarBut(7);//used for testing agnostically to the csv
								
								scheduler.inputE.add(order);
								
							}
							break;
							
						case 2:
							scheduler.console("E C2");
							//System.out.println("E C2");
							scheduler.inputE.add(order);
							
					

					}
					
				}
				state = ElevatorState.WAITING;
				scheduler.notifyAll();
				

			}

		}

	}

}