package mainCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Elevator implements Runnable {
	public Scheduler scheduler = new Scheduler();
	private int carNum;//Car number
	private int currFloor;//Floor the elevator is on currently
	private int button;//the button of the desired floor
	public LinkedList<Instruction> orders = new LinkedList<Instruction>();

	public Elevator(Scheduler scheduler, int carNum) {
		this.scheduler = scheduler;
		this.carNum = carNum;
	}
	
	public int getCarNum() {
		return this.carNum;
	}
	
	public int getFloor() {
		return this.currFloor;
	}
	
	public void setFloor(int floor) {
		this.currFloor = floor;
	}
	
	public int getButton() {
		return this.button;
	}
	
	public void setButton(int button) {
		this.button = button;
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
				while (scheduler.outputE.isEmpty() && orders.isEmpty()) {
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
					if (order.getType() == 0) {
						order.setCarCur(currFloor);
						order.setCarNum(carNum);
						//if the elevator is on the same floor as the calling floor the button can be pressed
						if (order.getFloor() == currFloor) {
							order.setCarBut(readInputFile("inputFile.csv"));
						} else {
							order.setCarBut(-1);
						}
						scheduler.inputE.add(order);
					} else {
						orders.add(order);
					}
					scheduler.notifyAll();
				}
				//if the elevator has orders
				if (!orders.isEmpty()) {
					if (orders.peek().getCarNum() == carNum) {
						Instruction currOrder = orders.pop();
						switch (currOrder.getType()) {
						//Orders from a button
						case (1):
							System.out.println("Elevator " + currOrder.getCarNum() + " is going to floor "
									+ currOrder.getfloorOrder());
							currFloor = currOrder.getfloorOrder();
							scheduler.acknowledged.add(currOrder);
							break;

						//Moving to the button call
						case (2):
							System.out.println("Elevator " + currOrder.getCarNum() + " is going to floor "
									+ currOrder.getfloorOrder() + ", now waiting for button");
							currFloor = currOrder.getfloorOrder();
							currOrder.setCarCur(currFloor);
							currOrder.setCarBut(readInputFile("inputFile.csv"));
							currOrder.setType(0);
							scheduler.inputE.add(currOrder);
							break;

						}

					}
					scheduler.notifyAll();

				}
				/*
				 * if(!scheduler.outputE.isEmpty()) { instruction order =
				 * scheduler.outputE.pop(); if(order.getType() == 0) {
				 * order.setCarCur(currFloor); order.setCarBut(button); order.setCarNum(carNum);
				 * scheduler.inputE.add(order); }else { orders.add(order); }
				 * scheduler.notifyAll(); } if(!orders.isEmpty()) {
				 * if(orders.peek().getfloorOrder()==carNum) { instruction currOrder =
				 * orders.pop(); System.out.println("Elevator " + currOrder.getCarNum() +
				 * " is going to floor " + currOrder.getfloorOrder()); }
				 * 
				 * }
				 */

			}

		}

	}

}
