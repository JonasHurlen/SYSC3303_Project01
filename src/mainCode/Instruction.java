package mainCode;
import java.sql.Timestamp;

public class Instruction {
	Timestamp timestamp = new Timestamp(0);
	private int floor;//floor instruction originated from
	private int floorBut;//direction of travel (0 is down, 1 is up)
	private int carCur;//current floor of the car
	private int carBut;//last button pressed in car
	private int type = 0;//Flag for current instruction interpretation (0 is instruction, 1 is movement from button, 2 is movement to floor)
	private int carNum;//number of the car
	private int floorOrder;//next position the car should go to
	public Instruction(int floor, int floorBut){
		this.floor = floor;
		this.floorBut = floorBut;
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
	
	public int getfloorOrder() {
		return floorOrder;
	}
	
	public void setfloorOrder(int floorOrder) {
		this.floorOrder = floorOrder;
	}
}
