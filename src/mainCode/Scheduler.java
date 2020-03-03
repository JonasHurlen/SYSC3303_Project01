package mainCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;

public class Scheduler implements Runnable {

	LinkedList<Instruction> inputF = new LinkedList<Instruction>();// input from floor
	LinkedList<Instruction> inputE = new LinkedList<Instruction>();// input from elevator
	LinkedList<Instruction> outputE = new LinkedList<Instruction>();// output to elevator
	LinkedList<Instruction> acknowledged = new LinkedList<Instruction>();// output to floor
	public LinkedList<Instruction>[] orders;
	private Car[] cars;
	// private ElevatorOrder
	// private SchedulerState state;
	private int numFloors;
	private boolean hasInit = false;

	public Scheduler() {
		Properties prop = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
			prop.load(inputStream);
			System.out.println(prop.getProperty("CARS"));
			// orders = new LinkedList[Integer.parseInt(prop.getProperty("CARS"))];
			this.orders = new LinkedList[4];
			// this.numFloors = Integer.parseInt(prop.getProperty("FLOORS"));
			this.numFloors = 22;

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// state = SchedulerState.WAITING;

	}

	public void writeToElevator(Instruction ins) {

	}

	public Instruction readFromElevator() {

		return null;
	}

	public void writeToFloor(Instruction ins) {

	}

	public Instruction readFromFloor() {

		return null;
	}

	public void elevatorRun(int carNum) {
		// synchronized(read)
		while (!this.orders[carNum].isEmpty()) {
			int next = -1;
			Instruction nextOrder = null;
			Car currCar = cars[carNum];
			int currDir = currCar.getDir();

			for (Instruction ins : this.orders[carNum]) {
				if (currDir == ins.getDirection() || currDir == -1) {
					int dist = Math.abs(currCar.getCurrFloor() - ins.getfloorOrder());
					if (next == -1 || dist < next) {
						next = dist;
						nextOrder = ins;
					}
				}
			}
			if (nextOrder != null) {
				switch (currCar.getType()) {
				case 0:
					// Should never get here
				case 1:
					// If the car is on the floor it is supposed to be on, open the doors
					if (currCar.getCurrFloor() == nextOrder.getFloor()
							|| currCar.getCurrFloor() == nextOrder.getCarBut()) {
						nextOrder.setType(2);
					} else {
						// Else start it moving
						if (nextOrder.getDirection() == 0) {
							nextOrder.setType(6);
						} else {
							nextOrder.setType(5);
						}
					}
					writeToElevator(nextOrder);
					break;

				}
			}

		}
	}

	@Override
	public void run() {
		synchronized (this) {
			while (true) {
				// while there are no pending instructions
				while (this.inputE.isEmpty() && this.inputF.isEmpty()) {
					try {
						// state = SchedulerState.BLOCKED;
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!this.inputF.isEmpty()) {
					// Takes information from floor and sends it through to the elevators to receive
					// info
					// state = SchedulerState.BUSY;
					Instruction order = inputF.pop();
					if (order.getDirection() == 0) {
						console(order.getFloor() + " going down");
					} else {
						console(order.getFloor() + " going up");
					}

					if (!hasInit) {
						outputE.add(order);
					} else {
						int carNum = schedule(order.getFloor(), order.getDirection(), cars);
						order.setCarNum(carNum);
						orders[carNum].add(order);
					}

					// state = SchedulerState.WAITING;

				}

				if (!this.inputE.isEmpty()) {
					// Receives complete instruction and then sorts it to an elevator
					// Stuff to figure out which elevator it goes to
					// state = SchedulerState.BUSY;
					Instruction order = inputE.pop();
					switch (order.getType()) {
					case 0:
						console("Recieved Poll");
						int distance = numFloors;
						cars = order.getCarPoll();
						for (Car car : cars) {
							if (car.getDir() == order.getDirection() || car.getDir() == 0) {
								if (car.getCurrFloor() == order.getFloor()) {
									order.setType(1);
									order.setCarNum(car.getId());
									outputE.add(order);
								}
								// checks to see nearest car to the call, will be optimized later
								if (distance < Math.abs(car.getCurrFloor() - order.getFloor())) {
									distance = Math.abs(car.getCurrFloor() - order.getFloor());
									order.setCarNum(car.getId());
								}

							}

						}
						if (order.getFloor() > order.getCarCur()) {
							order.setMove(1);
						} else if (order.getFloor() < order.getCarCur()) {
							order.setMove(-1);
						}
						console(order.getMove());
						this.outputE.add(order);

						break;

					case 1:
						console("S C1");
						int currCar = order.getCarNum();
						if (orders[currCar] == null) {
							orders[currCar] = new LinkedList<Instruction>();
						}
						order.setType(2);
						orders[currCar].add(order);// fit new destination into schedule
						console(order.getMove());
						break;
					case 2:
						console("S C2");
						if (!orders[order.getCarNum()].isEmpty()) {
							if (order.getCarBut() == order.getCarCur()) {
								this.acknowledged.add(order);
							}
							int next = orders[order.getCarNum()].peek().getCarBut();
							if (order.getCarCur() < next) {
								order.setMove(1);
								this.outputE.add(order);
							} else if (order.getCarCur() < next) {
								order.setMove(-1);
								this.outputE.add(order);
							}

						}
						console(order.getMove());
						break;

					}

				}
				// state = SchedulerState.WAITING;
				this.notifyAll();

			}

		}

	}

	public void console(Object in) {
		System.out.println(in);
	}

	private int schedule(int floor, int dir, Car[] cars) {
		int distance = -1;
		int carNum = -1;
		for (Car car : cars) {
			if (car.getDir() == dir) {
				if (dir == 0) {
					int carFloor = car.getCurrFloor();
					if (carFloor > floor) {
						if (distance < Math.abs(carFloor - floor) || distance == -1) {
							distance = Math.abs(carFloor - floor);
							carNum = car.getId();
						}
					}
				} else {
					int carFloor = car.getCurrFloor();
					if (carFloor < floor) {
						if (distance < Math.abs(carFloor - floor) || distance == -1) {
							distance = Math.abs(carFloor - floor);
							carNum = car.getId();
						}
					}
				}
			}
		}
		return carNum;
	}

}