package mainCode;

public class Car {
		private int id;
		int passengers;
		int destination;
		private int currFloor;
		int button;
		private int direction = 0;
		protected Car(int id) {
			this.id = id;
		}
		
		protected int getId() {
			return this.id;
		}
		protected int getCurrFloor() {
			return this.currFloor;
		}
		
		protected void setCurrFloor(int currFloor) {
			this.currFloor = currFloor;
		}
		protected int getDir() {
			return direction;
		}
		protected void setDir(int dir) {
			this.direction = dir;
		}
		
		
	}