import java.util.LinkedList;

public class Elevator implements Runnable {
	Scheduler scheduler = new Scheduler();
	int carNum;//Car number
	int currFloor;//Floor the elevator is on currently
	int button;//the button of the desired floor
	LinkedList<instruction> orders = new LinkedList<instruction>();

	Elevator(Scheduler scheduler, int carNum) {
		this.scheduler = scheduler;
		this.carNum = carNum;
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
					instruction order = scheduler.outputE.pop();
					//if the instruction is of type 0 return the values from the elevator
					if (order.getType() == 0) {
						order.setCarCur(currFloor);
						order.setCarNum(carNum);
						//if the elevator is on the same floor as the calling floor the button can be pressed
						if (order.getFloor() == currFloor) {
							order.setCarBut(button);
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
						instruction currOrder = orders.pop();
						switch (currOrder.getType()) {
						//Orders from a button
						case (1):
							System.out.println("Elevator " + currOrder.getCarNum() + " is going to floor "
									+ currOrder.getfloorOrder());
							currFloor = currOrder.getfloorOrder();
							break;

						//Moving to the button call
						case (2):
							System.out.println("Elevator " + currOrder.getCarNum() + " is going to floor "
									+ currOrder.getfloorOrder() + ", now waiting for button");
							currFloor = currOrder.getfloorOrder();
							currOrder.setCarCur(currFloor);
							currOrder.setCarBut(button);
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
