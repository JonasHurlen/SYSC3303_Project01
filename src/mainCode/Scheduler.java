package mainCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;

public class Scheduler implements Runnable {

	LinkedList<Instruction> inputF = new LinkedList<Instruction>();// input from floor
	LinkedList<Instruction> inputE = new LinkedList<Instruction>();// input from elevator
	LinkedList<Instruction> pendingInstruction = new LinkedList<Instruction>();// input from elevator
	LinkedList<Instruction> outputE = new LinkedList<Instruction>();// output to elevator
	LinkedList<Instruction> acknowledged = new LinkedList<Instruction>();// output to floor
	public LinkedList<Instruction>[] orders;
	private boolean[] outSwitch;
	private Car[] cars;
	private SchedulerState state;
	private int numFloors;
	private boolean hasInit = false;

	public Scheduler() {
		Properties prop = new Properties();
		FileInputStream ip;
		try {
			ip = new FileInputStream("config.properties");
			prop.load(ip);
			orders = new LinkedList[Integer.parseInt(prop.getProperty("CARS"))];
			this.numFloors = Integer.parseInt(prop.getProperty("FLOORS"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sets initial position of all cars
		for (int i = 0; i < orders.length; i++) {
			orders[i] = new LinkedList<Instruction>();
			// orders[i].add(new Instruction(1, 0));
			inputE.add(new Instruction(1, 0));
		}
		cars = new Car[Integer.parseInt(prop.getProperty("CARS"))];
		for (int i = 0; i < Integer.parseInt(prop.getProperty("CARS")); i++) {
			cars[i] = new Car(i);
			cars[i].setCurrFloor(1);
			cars[i].setDir(-1);
		}
		outSwitch = new boolean[Integer.parseInt(prop.getProperty("CARS"))];
		state = SchedulerState.WAITING;

	}

	@Override
	public void run() {

		// TODO Auto-generated method stub
		synchronized (this) {
			while (true) {
				// while there are no pending instructions
				while (this.inputE.isEmpty() && this.inputF.isEmpty()) {
					try {
						state = SchedulerState.BLOCKED;
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (!this.inputF.isEmpty()) {
					// Takes information from floor and sends it through to the elevators to receive
					// info
					state = SchedulerState.BUSY;
					Instruction instruction = inputF.pop();

					if (instruction.getFloorBut() == 0) {
						console(instruction.getFloor() + " going down");
					} else {
						console(instruction.getFloor() + " going up");
					}

					// System.out.println("Checking list 3: " + orders[3].peek().getFloor());
					// addOrder(orders[schedule(instruction.getFloor(), instruction.getCarBut(),
					// cars)], instruction);
					instruction.setCarNum(schedule(instruction.getFloor(), instruction.getCarBut(), cars));
					inputE.add(instruction);
					state = SchedulerState.WAITING;

				}

				if (!this.inputE.isEmpty()) {

					// Receives complete instruction and then sorts it to an elevator
					// Stuff to figure out which elevator it goes to
					state = SchedulerState.BUSY;
					Instruction order = inputE.pop();
					// System.out.println("Pulled from input E");
					int carCurr = order.getCarNum();
					addOrder(orders[carCurr], order);
						if (!outSwitch[carCurr]) {
							outSwitch[carCurr] = true;
							cars[carCurr].setCurrFloor(order.getFloor());
							cars[carCurr].setDir(order.getFloorBut());
							Instruction currOrder = orders[carCurr].pop();
							System.out.println("Car " + carCurr + " has type : " + currOrder.getType());

							switch (currOrder.getType()) {
							case 0:
								// If the car is idle and has not received passengers, initiate the open
								// procedure

								if (currOrder.getFloor() == currOrder.getCarCur()) {
									currOrder.setType(1);
									writeToElevator(currOrder);

								} else {
									// move
									if (currOrder.getFloorBut() == 0) {
										currOrder.setType(5);
										writeToElevator(currOrder);
									} else if (currOrder.getFloorBut() == 1) {
										currOrder.setType(4);
										writeToElevator(currOrder);
									}
								}
								break;

							case 1:
								// door has opened
								currOrder.setType(2);
								writeToElevator(currOrder);
								break;
							case 2:
								// door is open, loading
								if (!currOrder.getHasPass()) {
									currOrder.setHasPass(true);
									currOrder.setType(3);
								} else {
									currOrder.setType(6);
								}

								writeToElevator(currOrder);
								break;
							case 3:
								// door has closed
								currOrder.setType(0);
								writeToElevator(currOrder);
								break;
							case 4:
								if (currOrder.getFloor() == currOrder.getCarCur()) {
									currOrder.setType(0);
									writeToElevator(currOrder);

								} else {
									// move
									currOrder.setType(4);
									writeToElevator(currOrder);

								}
								break;
							case 5:
								if (currOrder.getFloor() == currOrder.getCarCur()) {
									currOrder.setType(0);
									writeToElevator(currOrder);

								} else {
									// move
									currOrder.setType(5);
									writeToElevator(currOrder);

								}
								break;
							case 6:
								// end of instruction
								writeToFloor(currOrder);
							
						}
					}

				}
				state = SchedulerState.WAITING;
				this.notifyAll();

			}

		}

	}

	private int schedule(int floor, int dir, Car[] cars) {
		// System.out.println(floor);
		// System.out.println(dir);
		int distance = -1;
		int carNum = -1;
		int numOrders = -1;
		for (Car car : cars) {
			if (car.getDir() == dir || car.getDir() == -1) {
				// System.out.println("Car " + car.getId() + " going " + car.getDir());
				if (car.getDir() == 0) {
					// System.out.println("down");
					int carFloor = car.getCurrFloor();
					if (carFloor > floor) {
						if (distance < Math.abs(carFloor - floor) || distance == -1) {
							distance = Math.abs(carFloor - floor);
							System.out.println("Dir 0, " + distance);
							carNum = car.getId();
						}
					}
				} else if (car.getDir() == 1) {
					// System.out.println("up");
					int carFloor = car.getCurrFloor();
					if (carFloor < floor) {
						if (distance < Math.abs(carFloor - floor) || distance == -1) {
							distance = Math.abs(carFloor - floor);
							System.out.println("Dir 1, " + distance);
							carNum = car.getId();
						}
					}
				} else {
					// System.out.println("no where");
					if (orders[car.getId()].isEmpty()) {
						carNum = car.getId();
					} else {
						if (orders[car.getId()].size() < numOrders || numOrders == -1) {
							carNum = car.getId();
						}
					}

				}
			}
		}
		System.out.println(carNum);
		return carNum;
	}

	public void console(Object in) {
		System.out.println(in);
	}

	private void readFromElevator() {
		// reading stuff
		Instruction incoming = pendingInstruction.pop();
		inputE.add(incoming);
		outSwitch[incoming.getCarNum()] = false;
	}

	private void writeToElevator(Instruction ins) {
		// reading stuff
		outputE.add(ins);
		pendingInstruction.add(ins);
	}

	private void readFromFloor() {
		// reading stuff
		inputE.add(pendingInstruction.pop());
	}

	private void writeToFloor(Instruction ins) {
		// reading stuff

		pendingInstruction.add(ins);
	}

	private void addOrder(LinkedList<Instruction> orderList, Instruction newIns) {
		int destination = -1;
		if (newIns.getHasPass()) {
			destination = newIns.getCarBut();
		} else {
			destination = newIns.getFloor();
		}
		if (orderList.isEmpty()) {
			orderList.add(newIns);
		} else {
			if (orderList.peekFirst().getCarBut() == 0) {
				for (Instruction ins : orderList) {
					if (ins.getHasPass()) {
						if (ins.getCarBut() >= destination) {
							orderList.add(orderList.indexOf(ins) - 1, newIns);
							break;
						}

					} else {
						if (ins.getHasPass()) {
							if (ins.getFloor() >= destination) {
								orderList.add(orderList.indexOf(ins) - 1, newIns);
								break;
							}
						}
					}
				}
			} else if (orderList.peekFirst().getCarBut() == 1) {
				for (Instruction ins : orderList) {
					if (ins.getHasPass()) {
						if (ins.getCarBut() <= destination) {
							orderList.add(orderList.indexOf(ins) - 1, newIns);
							break;
						}
					} else {
						if (ins.getHasPass()) {
							if (ins.getFloor() <= destination) {
								orderList.add(orderList.indexOf(ins) - 1, newIns);
								break;
							}
						}
					}
				}
			} else {
				orderList.add(newIns);
			}

		}

	}

}