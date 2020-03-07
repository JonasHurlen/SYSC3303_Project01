package mainCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ElevatorSubsystem implements Runnable {

	public Scheduler scheduler;
	private int numCars;// Car number
	public Car[] cars;
	private ElevatorState state;
	private boolean[] elevatorLamp;
	private boolean[] doorOpen;
	Properties prop = new Properties();

	/**
	 * Public constructor for class elevator subsystem
	 *
	 * @param scheduler scheduler used to schedule the relevant elevators
	 * @param numCars car number
	 */
	public ElevatorSubsystem(Scheduler scheduler) {
		this.scheduler = scheduler;
		FileInputStream ip;
		try {
			ip = new FileInputStream("config.properties");
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// this.numCars = numCars;
		numCars = Integer.parseInt(prop.getProperty("CARS"));
		System.out.println("There are " + this.numCars + " elevators running");
		cars = new Car[numCars];
		for (int i = 0; i < numCars; i++) {
			cars[i] = new Car(i);
			cars[i].setCurrFloor(1);
			cars[i].setDir(-1);
		}
		elevatorLamp = new boolean[numCars];
		doorOpen = new boolean[numCars];
		state = ElevatorState.WAITING;
	}
	
	/**
	 * Gets the car number of the elevator
	 *
	 * @return car number
	 */
	public int getCarNum() {
		return this.numCars;
	}

	/**
	 * Gets the car ID of the elevator
	 *
	 * @param carID carID of the elevator ranging from 0 to carNum-1
	 * @return the Car of the relevant elevator
	 * @throws IndexOutOfBoundsException if carNum not within range
	 */
	public Car getCar(int carID) {
		if (carID < 0 || carID >= numCars)
			throw new IndexOutOfBoundsException();

		return this.cars[carID];
	}

	/**
	 * Reads the inputted .txt file
	 *
	 * @return the inputted data obtained from the .txt file
	 * @throws FileNotFoundException if the .txt cannot be found
	 * @throws IOException if the .txt file cannot be read
	 */
	public static List<String> readInputFile() {
		// Integer myInt = null;
		List<String> inputData = new ArrayList<String>();
		FileReader input = null;
		try {
			input = new FileReader("inputFile.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		BufferedReader buff = new BufferedReader(input);
		String myLine = null;

		try {
			while ((myLine = buff.readLine()) != null) {
				String[] info = myLine.split(" ");
				String destinationFloor = info[3];
				inputData.add(destinationFloor);
				// System.out.println((Integer.parseInt(x.get(0))));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inputData;
	}

	public void sendToElevator(int car, int floor, int error) {
		
	}
	
	/**
	 * Runs the elevator subsystem
	 *
	 * @throws InterruptedException if thread is inaccessible
	 */
	public void run() {
		while (true) {
			synchronized (scheduler) {
				// wait while both the input and output lists are empty
				while (scheduler.outputE.isEmpty() && !scheduler.inputE.isEmpty()) {// will change to individual write
																					// queues
					try {
						state = ElevatorState.BLOCKED;
						scheduler.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// if there is something in the incoming instructions
				if (!scheduler.outputE.isEmpty()) {
					//System.out.println("Elevator Reading");
					state = ElevatorState.BUSY;
					Instruction order = scheduler.outputE.pop();
					int type = order.getType();
					int car = order.getCarNum();
					switch (type) {
					case 0:
						// Stop/idle
						scheduler.console(car + " is on " + order.getCarCur());
						scheduler.inputE.add(order);
						break;
					case 1:
						System.out.println("Car " + car + " Doors Opened on floor " + order.getCarCur());
						doorOpen[car] = true;
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						scheduler.inputE.add(order);
						break;
					case 2:
						//
						// order.setCarBut(readInputFile("inputFile.csv"));
						//

						List<String> x = readInputFile();
						// x is [ 4, 5]
						// only destination floors from inputFile column 4
						
						//
						order.setCarBut(Integer.parseInt(x.get(order.getInstructionID())));
						
						scheduler.console("Doors are open, someone gets on and requests floor " + order.getCarBut());
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						scheduler.inputE.add(order);
						break;

					case 3:
						System.out.println("Car " + car + ", doors closed on floor " + order.getCarCur());
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						doorOpen[car] = false;
						scheduler.inputE.add(order);
						break;
					case 4:
						// Move elevator up

						cars[car].setCurrFloor(cars[car].getCurrFloor() + 1);
						order.setCarCur(cars[order.getCarNum()].getCurrFloor());
						System.out.println("Car " + order.getCarNum() + " moved up to " + cars[car].getCurrFloor());
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						scheduler.inputE.add(order);
						break;
					case 5:
						// Move elevator down

						cars[car].setCurrFloor(cars[car].getCurrFloor() - 1);
						order.setCarCur(cars[order.getCarNum()].getCurrFloor());
						System.out.println("Car " + order.getCarNum() + " moved down to " + cars[car].getCurrFloor());
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						scheduler.inputE.add(order);
						break;

				
						
					case 6:
						System.out.println("Car " + car + ", doors closed on floor " + order.getCarCur());
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						doorOpen[car] = false;
						scheduler.inputE.add(order);
						break;
						
					case 7:
						System.out.println("Car " + car + ", doors are open on floor " + order.getCarCur() + ", people get off");
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						scheduler.inputE.add(order);
						break;
						
					}
					

				}
				state = ElevatorState.WAITING;
				scheduler.notifyAll();

			}

		}

	}

}
