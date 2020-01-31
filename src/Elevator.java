import java.util.LinkedList;

public class Elevator implements Runnable {
	Scheduler scheduler = new Scheduler();
	int carNum;
	int currFloor;
	int button;
	LinkedList<instruction> orders = new LinkedList<instruction>();

	Elevator(Scheduler scheduler, int carNum) {
		this.scheduler = scheduler;
		this.carNum = carNum;
	}

	public void run() {
		while (true) {
			synchronized (scheduler) {
				while (scheduler.outputE.isEmpty() && orders.isEmpty()) {
					try {
						scheduler.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (!scheduler.outputE.isEmpty()) {
					instruction order = scheduler.outputE.pop();
					if (order.getType() == 0) {
						order.setCarCur(currFloor);
						order.setCarNum(carNum);
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
				if (!orders.isEmpty()) {
					if (orders.peek().getCarNum() == carNum) {
						instruction currOrder = orders.pop();
						switch (currOrder.getType()) {
						case (1):
							System.out.println("Elevator " + currOrder.getCarNum() + " is going to floor "
									+ currOrder.getfloorOrder());
							currFloor = currOrder.getfloorOrder();
							break;

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
