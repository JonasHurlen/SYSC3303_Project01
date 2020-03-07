package mainCode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;

public class Scheduler implements Runnable {

	LinkedList<Instruction> inputF = new LinkedList<Instruction>();// input from floor
	LinkedList<Instruction> inputE = new LinkedList<Instruction>();// input from elevator
	Instruction[] pending;
	LinkedList<Instruction> outputE = new LinkedList<Instruction>();// output to elevator
	LinkedList<Instruction> acknowledged = new LinkedList<Instruction>();// output to floor
	public LinkedList<Instruction>[] orders;
	private Boolean[] outSwitch;
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
			pending = new Instruction[Integer.parseInt(prop.getProperty("CARS"))];
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sets initial position of all cars
		for (int i = 0; i < orders.length; i++) {
			orders[i] = new LinkedList<Instruction>();
			// addOrder(orders[i],new Instruction(1, 0));
			// inputE.add(new Instruction(1, 0));
		}
		cars = new Car[Integer.parseInt(prop.getProperty("CARS"))];
		outSwitch = new Boolean[Integer.parseInt(prop.getProperty("CARS"))];
		for (int i = 0; i < Integer.parseInt(prop.getProperty("CARS")); i++) {
			cars[i] = new Car(i);
			cars[i].setCurrFloor(1);
			cars[i].setDir(-1);
			outSwitch[i] = false;
		}
		state = SchedulerState.WAITING;

	}

	public boolean blockedState() {
		for (int i = 0; i < orders.length; i++) {
			if (outSwitch[i] && orders[i].isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {

		// TODO Auto-generated method stub
		synchronized (this) {
			while (true) {
				// while there are no pending instructions
				while (this.inputE.isEmpty() && this.inputF.isEmpty() && blockedState()) {
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
						console("Car " + instruction.getFloor() + " is going down");
					} else {
						console("Car " + instruction.getFloor() + " is going up");
					}
					int currCar = schedule(instruction.getFloor(), instruction.getCarBut(), cars);
					addOrder(orders[currCar], instruction);

					orders[currCar].peek().setCarNum(currCar);
					state = SchedulerState.WAITING;

				}

				if (!this.inputE.isEmpty()) {

					// Receives complete instruction and then sorts it to an elevator
					// Stuff to figure out which elevator it goes to
					state = SchedulerState.BUSY;
					Instruction order = inputE.pop();
					int carCurr = order.getCarNum();
					addOrder(orders[carCurr], order);

					// temp until udp
					outSwitch[carCurr] = false;
				}
				for (int carCurr = 0; carCurr < outSwitch.length; carCurr++) {
					if (!outSwitch[carCurr] && !orders[carCurr].isEmpty()) {
						Instruction currOrder = orders[carCurr].pop();
						outSwitch[carCurr] = true;
						cars[carCurr].setCurrFloor(currOrder.getFloor());
						cars[carCurr].setDir(currOrder.getFloorBut());

						switch (currOrder.getType()) {
						case 0:
							// If the car is idle and has not received passengers, initiate the open
							// procedure
							if (!currOrder.getHasPass()) {
								if (currOrder.getFloor() == currOrder.getCarCur()) {
									currOrder.setType(1);
									writeToElevator(currOrder);
								} else {
									// move
									if (currOrder.getCarCur() > currOrder.getFloor()) {
										currOrder.setType(5);
										writeToElevator(currOrder);
									} else if (currOrder.getCarCur() < currOrder.getFloor()) {
										currOrder.setType(4);
										writeToElevator(currOrder);
									}
								}

							} else {
								if (currOrder.getCarBut() == currOrder.getCarCur()) {
									currOrder.setType(1);
									writeToElevator(currOrder);
								} else {
									if (currOrder.getCarCur() > currOrder.getCarBut()) {
										currOrder.setType(5);
										writeToElevator(currOrder);
									} else if (currOrder.getCarCur() < currOrder.getCarBut()) {
										currOrder.setType(4);
										writeToElevator(currOrder);
									}
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
							if (!currOrder.getHasPass()) {
								if (currOrder.getFloor() == currOrder.getCarCur()) {
									currOrder.setType(0);
									writeToElevator(currOrder);

								} else {
									// move
									currOrder.setType(4);
									writeToElevator(currOrder);

								}
							} else {
								if (currOrder.getCarBut() == currOrder.getCarCur()) {
									currOrder.setType(0);
									writeToElevator(currOrder);

								} else {
									// move
									currOrder.setType(4);
									writeToElevator(currOrder);

								}
							}
							break;
						case 5:
							if (!currOrder.getHasPass()) {
								if (currOrder.getFloor() == currOrder.getCarCur()) {
									currOrder.setType(0);
									writeToElevator(currOrder);

								} else {
									// move
									currOrder.setType(5);
									writeToElevator(currOrder);

								}
							} else {
								if (currOrder.getCarBut() == currOrder.getCarCur()) {
									currOrder.setType(0);
									writeToElevator(currOrder);

								} else {
									// move
									currOrder.setType(5);
									writeToElevator(currOrder);

								}
							}
							break;
						case 6:
							// end of instruction
							writeToFloor(currOrder);
							outSwitch[carCurr] = false;

						}
					}
				}

				state = SchedulerState.WAITING;
				this.notifyAll();

			}

		}

	}

	private int schedule(int floor, int dir, Car[] cars) {
		int distance = -1;
		int carNum = -1;
		int numOrders = -1;
		for (Car car : cars) {
			if (car.getDir() == dir || car.getDir() == -1) {
				if (car.getDir() == 0) {
					int carFloor = car.getCurrFloor();
					if (carFloor > floor) {
						if (distance < Math.abs(carFloor - floor) || distance == -1) {
							distance = Math.abs(carFloor - floor);
							System.out.println("Dir 0, " + distance);
							carNum = car.getId();
						}
					}
				} else if (car.getDir() == 1) {
					int carFloor = car.getCurrFloor();
					if (carFloor < floor) {
						if (distance < Math.abs(carFloor - floor) || distance == -1) {
							distance = Math.abs(carFloor - floor);
							System.out.println("Dir 1, " + distance);
							carNum = car.getId();
						}
					}
				} else {
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
		return carNum;
	}

	public void console(Object in) {
		System.out.println(in);
	}

	private void readFromElevator() {
		// reading stuff
		/*
		 * Instruction incoming = pending[carNum](); inputE.add(incoming);
		 * outSwitch[incoming.getCarNum()] = false;
		 */

	}

	private void writeToElevator(Instruction ins) {
		// reading stuff
		//System.out.println("Write to elevator");
		outputE.add(ins);

		pending[ins.getCarNum()] = ins;
	}

	private void readFromFloor() {
		// reading stuff
	}

	private void writeToFloor(Instruction ins) {
		// reading stuff
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