package mainCode;

import java.sql.Timestamp;

public class Instruction {
	private int instructionID;
	Timestamp timestamp = new Timestamp(0);
	private int floor;//floor instruction originated from
	private int floorBut;//direction of travel (0 is down, 1 is up, -1 is idle)
	private int carCur;//current floor of the car
	private int carBut;//last button pressed in car
	private int type = 0;//Flag for current instruction interpretation (0 is poll, 1 is receive button input, 2 is movement to a floor)
	private boolean hasPassenger = false; //Has picked up a passenger for the request, i.e. carBut has a value
	private int carNum;//number of the car
	//private int floorOrder;//next position the car should go to
	//private Car[] carPoll;
	private int move = 0;//how the car should move next
	
	/**
	 * Gets the ID of the instruction
	 *
	 * @return the instruction ID (line number)
	 */
	public int getInstructionID() {
		return instructionID;
	}
	
	/**
	 * Sets the ID of the instruction
	 *
	 * @return the instruction ID (line number)
	 */
	public void setInstructionID(int instructionID) {
		this.instructionID = instructionID;
	}
	
	/**
	 * Public constructor for class instruction
	 *
	 * @param floor floor number  
	 * @param floorBut button on floor stating up or down
	 * @param instructionID ID of the instruction (line number)
	 */
	public Instruction(int floor, int floorBut, int instructionID){
		this.floor = floor;
		this.floorBut = floorBut;
		this.instructionID = instructionID;
	}
	
	public Instruction(int instructionID, int carNum, int carCur, int type) { 
		this.type = type; 
		this.carNum =carNum; 
		this.carCur =carCur; 
		this.instructionID = instructionID; 
	} 
	
	/**
	 * Gets the time
	 *
	 * @return the current time
	 */
	public Timestamp getTime(){
		return timestamp;
	}
	
	/**
	 * Gets the floor number
	 *
	 * @return the floor number
	 */
	public int getFloor() {
		return floor;
	}
	
	/**
	 * Gets the button on floor stating up or down
	 *
	 * @return button on floor stating up or down
	 */
	public int getFloorBut() {
		return floorBut;
	}
	
	/**
	 * Gets the current car number
	 *
	 * @return the current car number
	 */
	public int getCarCur() {
		return carCur;
	}
	
	/**
	 * Sets the current car number
	 *
	 * @return the current car number
	 */
	public void setCarCur(int carCur) {
		this.carCur = carCur;
	}
	
	/**
	 * Gets whether the elevator has passengers
	 *
	 * @return true if passengers are present, false otherwise
	 */
	public boolean getHasPass() {
		return hasPassenger;
	}
	
	/**
	 * Sets whether the elevator has passengers
	 *
	 * @param hasPass true of false depending on if the car has picked up passengers
	 * @return true if passengers are present, false otherwise
	 */
	public void setHasPass(boolean hasPass) {
		this.hasPassenger = hasPass;
	}
	
	/**
	 * Gets the last button pressed in car
	 *
	 * @return the last button pressed in car
	 */
	public int getCarBut() {
		return carBut;
	}
	
	/**
	 * Sets the last button pressed in car
	 *
	 * @return the last button pressed in car
	 */ 
	public void setCarBut(int carBut) {
		this.carBut = carBut;
	}
	
	/**
	 * Gets the instruction type (0 is poll, 1 is receive button input, 2 is movement to a floor)
	 *
	 * @return the type of instruction (0 is poll, 1 is receive button input, 2 is movement to a floor)
	 */ 
	public int getType() {
		return type;
	}
	
	/**
	 * Sets the instruction type (0 is poll, 1 is receive button input, 2 is movement to a floor)
	 *
	 * @return the type of instruction (0 is poll, 1 is receive button input, 2 is movement to a floor)
	 */ 
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * Gets the number of the car
	 *
	 * @return the number of the car
	 */ 
	public int getCarNum() {
		return carNum;
	}
	
	/**
	 * Sets the number of the car
	 *
	 * @return the number of the car
	 */
	public void setCarNum(int CarNum) {
		this.carNum = CarNum;
	}

	/*
	public int getfloorOrder() {
		return floorOrder;
	}
	
	public void setfloorOrder(int floorOrder) {
		this.floorOrder = floorOrder;
	}
	*/
	
	/**
	 * Gets how the car should move next
	 *
	 * @return how the car should move next
	 */
	public int getMove() {
		return move;
	}
	
	
	/**
	 * Sets how the car should move next
	 *
	 * @return how the car should move next
	 */
	public void setMove(int Move) {
		this.move = Move;
	}
	
	/*
	 * public Car[] getCarPoll() { return carPoll; }
	 * 
	 * public void setCarPoll(Car[] carPoll) { this.carPoll = carPoll; }
	 */
}
