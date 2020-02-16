Elevator Scheduling System

Group 1 Submission for 3303 Project Iteration 2

Installation
	All java files and the .csv file must be included in the same project to ensure proper communication
	
To Run
	To begin running the program, run the start class and it will begin all threads
	The csv file can be filled with additional instructions for the program

Tests
	- The first test file is TestElevator, which can be run by right-clicking on the file and selecting
	- run as JUnit test. The second test file, TestInstruction, can be run in the same way.
	
Included Files
	Start.java- The main class of the program. Instantiates and runs all threads
	Scheduler.java- Allows communication between elevators and floors via instructions
	ElevatorSubsystem.java- Class representing the elevators. Can send and receive instructions and read information from the input file
	Car.java- Class used to represent individual elevator cars by the Elevator Subsystem
	FloorSubsystem.java- Class representing individual floors. Can send and receive instructions and read information from the input file
	Instruction.java- Class used to transfer information between other classes.
	ElevatorState.java - Enum class used to enumerate all possible states of elevator
	SchedulerState.java - Enum class used to enumerate all possible states of scheduler
	inputFile.csv- Contains a instructions for the operation of the simulation
	Iteration1 Documenation.pdf - contains class diagram and sequence diagram
	Iteration2 Documentation.pdf - contains all necessary diagrams for 
	Remaining files - There are 2 draw.io files, and 2 png files as a backup for the diagrams
	
Credits & Contributions
	Khalil Aalab: Implementation of state machine, organizing files in project
	Abdelrahman Darwish:  readInputFile method() to read input from CSV file
	Michael Fairbairn: JUnit Testing
	Jonas Hurlen: README, Scheduler communication & logic, Elevator framework
	Krishang Karir: Constructing class diagrams, sequence diagrams and state machine diagrams and documentation
