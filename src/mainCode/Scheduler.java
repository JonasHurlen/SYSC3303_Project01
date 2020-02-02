package mainCode;
import java.util.LinkedList;

public class Scheduler implements Runnable {

	LinkedList<Instruction> inputF = new LinkedList<Instruction>();//input from floor
	// LinkedList<instruction> outputF = new LinkedList<instruction>();
	LinkedList<Instruction> inputE = new LinkedList<Instruction>();//input from elevator
	LinkedList<Instruction> outputE = new LinkedList<Instruction>();//output to elevator
	LinkedList<Instruction> acknowledged = new LinkedList<Instruction>();//output to floor

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (this) {
			while (true) {
				while (this.inputE.isEmpty() && this.inputF.isEmpty()) {// while there are no pending instructions
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
					outputE.add(inputF.pop());
					
				}

				if (!this.inputE.isEmpty()) {
					// Receives complete instruction and then sorts it to an elevator
					// Stuff to figure out which elevator it goes to
					Instruction order = inputE.pop();
					if (order.getCarBut() == -1) {
						order.setfloorOrder(order.getFloor());
						order.setType(2);// Turns instruction into order
					} else {
						order.setfloorOrder(order.getCarBut());
						order.setType(1);// Turns instruction into order
					}

					outputE.add(order);
					
				}
				this.notifyAll();
				
				/*
				 * if(!this.inputE.isEmpty()) { //Receives complete instruction and then sorts
				 * it to an elevator //Stuff to figure out which elevator it goes to instruction
				 * order = inputE.pop(); order.setfloorOrder(order.getFloor());
				 * order.setType(1);//Turns instruction into order outputE.add(order);
				 * this.notifyAll(); }
				 */

			}

		}

	}

}
