package mainCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	public boolean[] elevatorLamp;
	Properties prop = new Properties();

	public ElevatorSubsystem(Scheduler scheduler) {
		this.scheduler = scheduler;
		FileInputStream ip;
		try {
			ip = new FileInputStream("config.properties");
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.numCars = Integer.parseInt(prop.getProperty("CARS"));
		cars = new Car[numCars];
		for (int i = 0; i < numCars; i++) {
			cars[i] = new Car(i);
			cars[i].setCurrFloor(1);
			cars[i].setDir(0);
		}
		elevatorLamp = new boolean[numCars];
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
		if (carID < 0 || carID >= numCars)
			throw new IndexOutOfBoundsException();

		return this.cars[carID];
	}

	public List<String> readInputFile(String filename) {
		Integer myInt = null;
		List<String> inputData = new ArrayList<String>();
		// String[] myArray = null;
		// int[] arr= null;
		try {
			Scanner scanner = new Scanner(new File(filename)); // for parsing through the lines
			scanner.useDelimiter(","); // sets the delimiter pattern

			while (scanner.hasNext()) {
				String x = scanner.next();
				inputData.add(x);
				Scanner lines = new Scanner(new File(filename)); // to get each line
				while (lines.hasNext()) {
					String y = scanner.next();
					inputData.add(y);

				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} finally {
			return inputData;
		}
	}

	public void run() {
		while (true) {
			synchronized (scheduler) {
				// wait while both the input and output lists are empty
				while (scheduler.outputE.isEmpty() && !scheduler.inputE.isEmpty()) {
					try {
						state = ElevatorState.BLOCKED;
						scheduler.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// if there is something in the incoming instructions
				if (!scheduler.outputE.isEmpty()) {
					Instruction order = scheduler.outputE.pop();
					int type = order.getType();
					int car = -1; // Sets to a default value, will never be used as -1
					if (type != 0) {
						car = order.getCarCur();
					}
					cars[car].setType(type);

					/*
					 * try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { // TODO
					 * Auto-generated catch block e.printStackTrace(); }
					 */
					switch (type) {
					case 0:
						// Scheduler polls the elevator system to acquire the positions of all cars
						scheduler.console("Initial Poll");
						order.setCarPoll(cars);
						scheduler.inputE.add(order);
						break;
					case 1:
						//Stop/idle
						scheduler.console(car + " is on " + order.getFloor());
						scheduler.inputE.add(order);
						break;
					case 2:
						// System opens the doors and allows someone to get on
						cars[car].setDoorState(1);

						List<String> x = readInputFile("inputFile.csv");
						// String elementone = x.get(2);
						// String elementtwo = x.get(6);
						// String elementthree = x.get(11);

						// Change String to ints

						// int p = Integer.parseInt(elementone);
						// int y = Integer.parseInt(elementtwo);
						// int z = Integer.parseInt(elementthree);
						// int[] myarray = {p,y};

						int[] myarray = { 5, 4 };

						for (int i = 0; i < 2; i++) {
							//
							order.setCarBut(myarray[i]);
							scheduler.console(car + " Doors open on " + order.getFloor() + ", someone presses a button");

							scheduler.inputE.add(order);

						}
						break;

					case 3:
						//Idle with open doors
						scheduler.console(car + " loading on " + order.getFloor());
						scheduler.inputE.add(order);
						break;
						
					case 4:
						// Close the door
						cars[car].setDoorState(0);
						scheduler.console(car + " Doors close on " + order.getFloor());
						scheduler.inputE.add(order);
						break;
					case 5:
						// Move elevator up
						
							cars[car].setCurrFloor(cars[car].getCurrFloor() + 1);
							order.setCarCur(cars[order.getCarNum()].getCurrFloor());
							System.out.println("Car " + order.getCarNum() + " moved up to " + cars[car].getCurrFloor());
						

						break;
						
					case 6:
						// Move elevator down
						
							cars[car].setCurrFloor(cars[car].getCurrFloor()-1);
							order.setCarCur(cars[order.getCarNum()].getCurrFloor());
							System.out.println("Car " + order.getCarNum() + " moved down to " + cars[car].getCurrFloor());
						

						break;
					}

				}
				state = ElevatorState.WAITING;
				scheduler.notifyAll();

			}

		}

	}

}