Elevator Scheduling System

Group 1 Submission for 3303 Project Iteration 3

Installation
	All java files and the .txt input file must be included in the same project to ensure proper communication
	
To Run
	To begin running the program, run the Scheduler class
	Next step is to run the ElevatorSubsystem class
	Lastly, run the FloorSubsystem class.
	The .txt input file can be filled with additional instructions for the program

Tests
	The first test file is TestElevator, which can be run by right-clicking on the file and selecting
	run as JUnit test. The second test file, TestInstruction, can be run in the same way, 
	as well as TestCar and TestFloorSubsystem. Additionally, there is TestFloorSubsystem
	and TestElevator test classes. 
	
Included Files
	ElevatorRead.java - A helper class that is a thread that 
	Start.java- The main class of the program. Instantiates and runs all threads, now redundant
	Scheduler.java- Allows communication between elevators and floors via instructions
	ElevatorSubsystem.java- Class representing the elevators. Can send and receive instructions and read information from the input file
	Car.java- Class used to represent individual elevator cars by the Elevator Subsystem
	FloorSubsystem.java- Class representing individual floors. Can send and receive instructions and read information from the input file
	Instruction.java- Class used to transfer information between other classes.
	ElevatorState.java - Enum class used to enumerate all possible states of elevator
	SchedulerState.java - Enum class used to enumerate all possible states of scheduler
	inputFile.csv- Contains a instructions for the operation of the simulation
	Iteration1 Documenation.pdf - contains class diagram and sequence diagram
	Iteration2 Documentation.pdf - contains all necessary diagrams
	Remaining files - There are 2 draw.io files, and 2 png files as a backup for the diagrams
	
Credits & Contributions
	Khalil Aalab: Assisting with reading input, as well as UDP, as well as debugging and integrating code
	Abdelrahman Darwish:  readInputFile method() for ElevatorSubsyem and FloorSubsystem, UDP Communication between Subsystems
	Michael Fairbairn: JUnit Testing
	Jonas Hurlen: README, Scheduler communication & logic (algorithm), Elevator framework
	Krishang Karir: Constructing class diagrams, sequence diagrams and state machine diagrams and documentation
	
Concurrency Between Iterations
The concurrency of the system changed from having a dedicated critical section policed by a synchronized method to being several threads that only interacted with each other through non-critical interfaces. 
Some additional concurrency is implemented via a limiting array of booleans to control access to writing per each elevator list.
