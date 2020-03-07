package mainCode;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class ElevatorRead implements Runnable {
	DatagramSocket receiveSocket;
	DatagramPacket receivePacket;
	ElevatorSubsystem master;
	ElevatorRead(ElevatorSubsystem master){
		this.master = master;
		 try {
			receiveSocket= new DatagramSocket(38594);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void run() {
		while (true) {
			byte data[] = new byte[1000];
			receivePacket = new DatagramPacket(data, data.length);
			System.out.println("Elevator: Waiting for Packet.\n");

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
			System.out.println("Elevator: Packet received:");
			System.out.println("From Scheduler: " + receivePacket.getAddress());
			System.out.println("Host port: " + receivePacket.getPort());
			int len = receivePacket.getLength();
			System.out.println("Length: " + len);
			System.out.print("Containing: ");

			// Form a String from the byte array.
			String received = new String(data, 0, len);
			System.out.println(received + "\n");

			
			String[] info = received.split(" ");
			int instructionID = Integer.parseInt(info[0]);
			int carNum = Integer.parseInt(info[1]);
			int carCur = Integer.parseInt(info[2]);
			int type = Integer.parseInt(info[3]);
			master.outputE.add(new Instruction(instructionID, carNum, carCur, type));
			//return new Instruction(instructionID, carNum, carCur, type);
	    }
		
	}

}
