package mainCode;

public class Car {
		private int id;
		int passengers;
		int destination;
		private int currFloor;
		int button;
		private int direction = 0;
	
		/**
		 * Public constructor for class Car
		 *
		 * @param id car id
		 */
		public Car(int id) {
			this.id = id;
		}
		
		/**
		 * Returns the id for the specified car
		 *
		 * @return the id
		 */
		public int getId() {
			return this.id;
		}
	
		/**
		 * Gets the current floor of the elevator
		 *
		 * @return the current floor
		 */
		public int getCurrFloor() {
			return this.currFloor;
		}
		
		/**
		 * Sets the current floor of the elevator
		 *
		 * @param currFloor current floor of the elevator
		 * @return the current floor
		 */
		public void setCurrFloor(int currFloor) {
			this.currFloor = currFloor;
		}
		
		/**
		 * Gets the direction of the elevator
		 *
		 * @return the direction
		 */
		public int getDir() {
			return direction;
		}
	
		/**
		 * Sets the direction of the elevator
		 *
		 * @param dir direction of the elevator
		 * @return the direction
		 */
		public void setDir(int dir) {
			this.direction = dir;
		}
	}
