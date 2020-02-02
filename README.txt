Elevator Scheduling System

Group 1 Submission for 3303 Project

Installation
	All java files and the csv must be included in the same package to ensure proper communication
	
To Run
	To begin running the program, run the start class and it will begin all threads
	The csv file can be filled with additional instructions for the program

Tests
	-
	-
	
Included Files
	Start.java- The main class of the program. Instantiates and runs all threads
	Scheduler.java- Allows communication between elevators and floors via instructions
	Elevator.java- Class representing the elevators. Can send and receive instructions and read information from the input file
	FloorSubsystem.java- Class representing individual floors. Can send and receive instructions and read information from the input file
	inputFile.csv- Contains a instructions for the operation of the simulation
	
Credits & Contributions
	Khalil Aalab: Setting up work environment, incorporating various members work, notifying Floor from Scheduler via acknowledged LinkedList
	Abdelrahman Darwish: FloorSubsytem thread, readInputFile method()
	Michael Fairbairn: JUnit Testing, separate packages, general code cleanup
	Jonas Hurlen: README, Scheduler communication & logic, Elevator framework
	Krishang Karir: Constructing class diagram, sequence diagram
