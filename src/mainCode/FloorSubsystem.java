package mainCode;
import java.io.*;
import java.io.*;
import java.net.*;

import java.util.*;

public class FloorSubsystem implements Runnable {

	private int floorNumber;
	private String[] myArray;
	private Scheduler scheduler;
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendReceiveSocket;
	
	/**
	 * Sends instructions betwen subsystems
	 *
	 * @param inst instruction to send
	 * @throws UnknownHostException if the host cannot be located
	 * @throws IOException if the socket cannot be read from
	 */
	public void send(Instruction inst) {
		String first = ((Integer) inst.getFloor()).toString();
		String second = ((Integer) inst.getFloorBut()).toString();
		String third = ((Integer) inst.getInstructionID()).toString();
		String message = first + " " + second + " " + third;
		byte[] msg = message.getBytes();

	      try {
	         sendPacket = new DatagramPacket(msg, msg.length,
	                                         InetAddress.getLocalHost(), 40979);
	      } catch (UnknownHostException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }
	      
	      System.out.println("Floor: Sending packet:");
	      System.out.println("To host: " + sendPacket.getAddress());
	      System.out.println("Destination host port: " + sendPacket.getPort());
	      int len = sendPacket.getLength();
	      System.out.println("Length: " + len);
	      System.out.print("Containing: ");
	      System.out.println(new String(sendPacket.getData(),0,len)); // or could print "s"

	      // Send the datagram packet to the server via the send/receive socket. 

	      try {
	         sendReceiveSocket.send(sendPacket);
	      } catch (IOException e) {
	         e.printStackTrace();
	         System.exit(1);
	      }

	      System.out.println("Floor: Packet sent.\n");
	      scheduler.readFromFloor();

		
		//int floor, int floorBut, int instructionID
		// ADD TIMESTAMP
		
	}

	/**
	 * Runs the floor subsystem
	 *
	 * @throws InterruptedException if thread is inaccessible
	 */
	public void run() {

		while (true) {
			synchronized (scheduler) {
				// wait while both the input and output lists are empty
				while (scheduler.acknowledged.isEmpty()) {
					try {
						scheduler.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				System.out.println("Request acknowledged by floor");
				scheduler.acknowledged.pop();
				scheduler.notifyAll();
			}

		}

	}
	
	/**
	 * Public constructor for class floor subsystem
	 *
	 * @param scheduler scheduler used to schedule the relevant elevators
	 * @param floorNumber floor number
	 */
	public FloorSubsystem(Scheduler scheduler) {
		this.scheduler = scheduler;
		try {
	         // Construct a datagram socket and bind it to any available 
	         // port on the local host machine. This socket will be used to
	         // send and receive UDP Datagram packets.
	         sendReceiveSocket = new DatagramSocket();
	      } catch (SocketException se) {   // Can't create the socket.
	         se.printStackTrace();
	         System.exit(1);
	      }
	

	}
	
	/**
	 * Starts reading the .txt file
	 *
	 */
	public void startReading() {
		int numberOfLines = 4;
		Instruction inst;
		for (int i = 0; i < numberOfLines; i++) {
			inst = readInputFile(i);
			send(inst);
		//scheduler.inputF.add(inst);			
		}
		
	}	
	
	
	
	/**
	 * Reads in events from .txt file to be sent to the scheduler 
	 *
	 * @return instructions obtained from .txt file
	 * @throws FileNotFoundException if the .txt file cannot be found
	 * @throws IOException if the input data cannot be read from
	 */
	public static Instruction readInputFile(int desiredLine) {
		
		//Integer myInt = null;
		List<String>inputData = new ArrayList<String>();
		FileReader input = null;
        try {
         input = new FileReader("inputFile.txt");
 	        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();
        	
        	 }
        BufferedReader buff = new BufferedReader(input);
        String myLine =null;
        Instruction instruction = null;
        
      try {
    	  		for (int i = 0; i < desiredLine; i++) {
    	  			myLine = buff.readLine();
    	  		}
        		myLine = buff.readLine();
        		String[] info = myLine.split(" ");
        		String destinationFloor = info[3]; 
        		inputData.add(destinationFloor);
        		//System.out.println((Integer.parseInt(x.get(0))));
        		instruction = new Instruction(Integer.parseInt(info[1]), Integer.parseInt(info[2]), desiredLine);
        	
      	}
        	catch(IOException e ) {
        		e.printStackTrace();
        	}
       
      return instruction;
	}

	

}
