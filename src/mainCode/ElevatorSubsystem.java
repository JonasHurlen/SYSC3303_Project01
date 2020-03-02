package mainCode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	public static List<String> readInputFile() {
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
        
      try {
        	while((myLine = buff.readLine()) !=null){
        		String[] info = myLine.split(" ");
        		String destinationFloor = info[3]; 
        		inputData.add(destinationFloor);
        		//System.out.println((Integer.parseInt(x.get(0))));
        		
        }
      }
        	catch(IOException e ) {
        		e.printStackTrace();
        	}
       
        
return inputData;
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
							
							List<String> x = readInputFile();
							//x is [ 4, 5] 
							// only destination floors from inputFile column 4
							for ( int i=0; i<2; i++) {
								//
								order.setCarBut(Integer.parseInt(x.get(i)));
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