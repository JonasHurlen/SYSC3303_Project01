package mainCode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SchedulerReadElevator implements Runnable{

	DatagramSocket receiveSocket;
	DatagramPacket receivePacket;
	Scheduler master;
	SchedulerReadElevator(Scheduler master){
		this.master = master;
		try {
	         // Construct a datagram socket and bind it to any available 
	         // port on the local host machine. This socket will be used to
	         // send and receive UDP Datagram packets.
			receiveSocket = new DatagramSocket(45892);
	      } catch (SocketException se) {   // Can't create the socket.
	         se.printStackTrace();
	         System.exit(1);
	      }
		
	}
	
	public void readFromElevator() {
		byte data[] = new byte[1000];
		receivePacket = new DatagramPacket(data, data.length);
		//System.out.println("Scheduler: Waiting for Packet from Elevator.\n");

		// Block until a datagram packet is received from receiveSocket.
		try {
			//System.out.println("Waiting..."); // so we know we're waiting
			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			//System.out.print("IO Exception: likely:");
			//System.out.println("Receive Socket Timed Out.\n" + e);
			e.printStackTrace();
			System.exit(1);
		}

		// Process the received datagram.
		//System.out.println("Scheduler: Packet received:");
		//System.out.println("From host: " + receivePacket.getAddress());
		//System.out.println("Host port: " + receivePacket.getPort());
		int len = receivePacket.getLength();
		//System.out.println("Length: " + len);
		//System.out.print("Containing: ");

		// Form a String from the byte array.
		String received = new String(data, 0, len);
		//System.out.println(received + "\n");

		// int floor, int floorBut, int instructionID
		String[] info = received.split(" ");
		
		int instructionID = Integer.parseInt(info[0]);
		int carNum = Integer.parseInt(info[1]);
		int carCur = Integer.parseInt(info[2]);
		int type = Integer.parseInt(info[3]);
		int carBut = Integer.parseInt(info[4]);
		
		System.out.println(instructionID + ", " + carNum + ", " + carCur + ", " + type + ", " + carBut);
		//Instruction instruction = new Instruction(instructionID, carNum, carCur, type);
		Instruction incoming = master.pending[carNum];
		if(carBut != -1) {
			incoming.setCarBut(carBut);
		}
		
		incoming.setType(type);
		
		
		
		
		master.inputE.add(incoming);
		master.outSwitch[incoming.getCarNum()] = false;
		//return incoming;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			System.out.println("ReadElevatorLoop");
			readFromElevator();
		}
		
	}
}
