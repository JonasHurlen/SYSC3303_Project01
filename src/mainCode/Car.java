package mainCode;

public class Car {
		private int id;
		int passengers;
		int destination;
		private int currFloor;
		int button;
		private int direction = 0;
		public Car(int id) {
			this.id = id;
		}
		
		public int getId() {
			return this.id;
		}
		public int getCurrFloor() {
			return this.currFloor;
		}
		
		public void setCurrFloor(int currFloor) {
			this.currFloor = currFloor;
		}
		public int getDir() {
			return direction;
		}
		public void setDir(int dir) {
			this.direction = dir;
		}
	}