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
	
	public int getInstructionID() {
		return instructionID;
	}

	public void setInstructionID(int instructionID) {
		this.instructionID = instructionID;
	}

	public Instruction(int floor, int floorBut, int instructionID){
		this.floor = floor;
		this.floorBut = floorBut;
		this.instructionID = instructionID;
	}
	
	public Timestamp getTime(){
		return timestamp;
	}
	
	public int getFloor() {
		return floor;
	}
	
	public int getFloorBut() {
		return floorBut;
	}
	
	public int getCarCur() {
		return carCur;
	}
	
	public void setCarCur(int carCur) {
		this.carCur = carCur;
	}
	
	public boolean getHasPass() {
		return hasPassenger;
	}
	
	public void setHasPass(boolean hasPass) {
		this.hasPassenger = hasPass;
	}
	
	public int getCarBut() {
		return carBut;
	}
	
	public void setCarBut(int carBut) {
		this.carBut = carBut;
	}
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getCarNum() {
		return carNum;
	}
	
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
	public int getMove() {
		return move;
	}
	
	public void setMove(int Move) {
		this.move = Move;
	}
	/*
	public Car[] getCarPoll() {
		return carPoll;
	}
	
	public void setCarPoll(Car[] carPoll) {
		this.carPoll = carPoll;
	}
	*/
}