package mainCode;

import java.util.LinkedList;

public class Scheduler implements Runnable {

	LinkedList<Instruction> inputF = new LinkedList<Instruction>();// input from floor
	LinkedList<Instruction> inputE = new LinkedList<Instruction>();// input from elevator
	LinkedList<Instruction> outputE = new LinkedList<Instruction>();// output to elevator
	LinkedList<Instruction> acknowledged = new LinkedList<Instruction>();// output to floor
	public LinkedList<Instruction>[] orders;
	private Car[] cars;
	private SchedulerState state;

	public Scheduler(int numCars, int numFloors) {
		orders = new LinkedList[numCars];
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
					
					if (inputF.peek().getFloorBut() == 0) {
						console(inputF.peek().getFloor() + " going down");
					} else {
						console(inputF.peek().getFloor() + " going up");
					}

					outputE.add(inputF.pop());
					state = SchedulerState.WAITING;

				}

				if (!this.inputE.isEmpty()) {
					// Receives complete instruction and then sorts it to an elevator
					// Stuff to figure out which elevator it goes to
					state = SchedulerState.BUSY;
					Instruction order = inputE.pop();
					switch (order.getType()) {
					case 0:
						console("S C0");
						int distance = 999;
						cars = order.getCarPoll();
						for (Car car : cars) {
							if (car.getDir() == order.getFloorBut() || car.getDir() == 0) {
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
				state = SchedulerState.WAITING;
				this.notifyAll();

			}

		}

	}

	public void console(Object in) {
		System.out.println(in);
	}

}