package mainCode;

import java.util.LinkedList;

public class Scheduler implements Runnable {

	LinkedList<Instruction> inputF = new LinkedList<Instruction>();// input from floor
	LinkedList<Instruction> inputE = new LinkedList<Instruction>();// input from elevator
	LinkedList<Instruction> outputE = new LinkedList<Instruction>();// output to elevator
	LinkedList<Instruction> acknowledged = new LinkedList<Instruction>();// output to floor

	LinkedList<Instruction> transIn = new LinkedList<Instruction>();// streamlined communication with elevators
	LinkedList<Instruction> transOut = new LinkedList<Instruction>();// streamlined communication with elevators
	public LinkedList<Instruction>[] orders;
	private Car[] cars;

	public Scheduler(int numCars, int numFloors) {
		orders = new LinkedList[numCars];
	}

	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		synchronized (this) {
			while (true) {
				while (this.inputE.isEmpty() && this.inputF.isEmpty() && this.transIn.isEmpty()) {// while there are no pending instructions
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
					System.out.println(inputF.peek().getFloor() + " going " + inputF.peek().getFloorBut());
					outputE.add(inputF.pop());
					

				}

				if (!this.inputE.isEmpty()) {
					// Receives complete instruction and then sorts it to an elevator
					// Stuff to figure out which elevator it goes to
					Instruction order = inputE.pop();
					switch (order.getType()) {
					case 0:
						System.out.println("S C0");
						int distance = 999;
						cars = order.getCarPoll();
						for (Car car : cars) {
							if (car.getDir() == order.getFloorBut()||car.getDir() == 0) {
								if (car.getCurrFloor() == order.getFloor()) {
									order.setType(1);
									order.setCarNum(car.getId());
									outputE.add(order);
								}
								if(distance < Math.abs(car.getCurrFloor() - order.getFloor())){
									distance = Math.abs(car.getCurrFloor() - order.getFloor());
									order.setCarNum(car.getId());
								}
								
								
							}

						}
						if(order.getFloorBut() == 0) {
							order.setMove(-1);
						}else {
							order.setMove(1);
						}
						
						if(orders[order.getCarNum()] == null) {
							orders[order.getCarNum()] = new LinkedList<Instruction>();
						}
						orders[order.getCarNum()].add(order);
						this.transIn.add(order);
						System.out.println(order.getMove());
						break;
						
					case 1:
						System.out.println("S C1");
						int currCar = order.getCarNum();
						if(orders[currCar] == null) {
							orders[currCar] = new LinkedList<Instruction>();
						}
						order.setType(2);
						orders[currCar].add(order);//fit new destination into schedule
						break;
					case 2:
						System.out.println("Order from car in wrong stream");
						break;
						
					}

					

				}
				
				
				for(Instruction curr : this.transIn) {
					cars[curr.getCarNum()].setCurrFloor(curr.getCarCur());
					if(curr.getCarBut() == curr.getCarCur()) {
						this.acknowledged.add(orders[curr.getCarNum()].pop());
					}
					int next = orders[curr.getCarNum()].peek().getCarBut();
					if(curr.getMove() == 0) {
						if(curr.getCarCur() < next) {
							curr.setMove(1);
							this.transOut.add(curr);
						}else if(curr.getCarCur() < next) {
							curr.setMove(-1);
							this.transOut.add(curr);
						}
					}
					
					
				}
				this.notifyAll();

			}

		}

	}

}