package mainCode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SchedulerReadFloor implements Runnable{
	DatagramSocket receiveSocket;
	DatagramPacket receivePacket;
	Scheduler master;
	SchedulerReadFloor(Scheduler master){
		this.master = master;
		try {
	         // Construct a datagram socket and bind it to any available 
	         // port on the local host machine. This socket will be used to
	         // send and receive UDP Datagram packets.
			receiveSocket = new DatagramSocket(40979);
	      } catch (SocketException se) {   // Can't create the socket.
	         se.printStackTrace();
	         System.exit(1);
	      }
		
	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			readFromFloor();
		}
	}
	public void readFromFloor() {
		byte data[] = new byte[1000];
	      receivePacket = new DatagramPacket(data, data.length);
	      System.out.println("Scheduler: Waiting for Packet.\n");

	      // Block until a datagram packet is received from receiveSocket.
	      try {        
	         System.out.println("Waiting..."); // so we know we're waiting
	         receiveSocket.receive(receivePacket);
	      } catch (IOException e) {
	         System.out.print("IO Exception: likely:");
	         System.out.println("Receive Socket Timed Out.\n" + e);
	         e.printStackTrace();
	         System.exit(1);
	      }

	      // Process the received datagram.
	      System.out.println("Scheduler: Packet received:");
	      System.out.println("From host: " + receivePacket.getAddress());
	      System.out.println("Host port: " + receivePacket.getPort());
	      int len = receivePacket.getLength();
	      System.out.println("Length: " + len);
	      System.out.print("Containing: " );

	      // Form a String from the byte array.
	      String received = new String(data,0,len);   
	      System.out.println(received + "\n");
	      
	    //int floor, int floorBut, int instructionID
	      String[] info = received.split(" ");
	      int floor = Integer.parseInt(info[0]);
	      int floorBut = Integer.parseInt(info[1]);
	      int instructionID = Integer.parseInt(info[2]);
	      Instruction instruction = new Instruction(floor, floorBut, instructionID);
	      master.inputF.add(instruction);
	      
	}

}
