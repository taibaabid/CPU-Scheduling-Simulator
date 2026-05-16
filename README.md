____________***CPU Scheduling Simulator***____________
A simple, interactive tool to see how an Operating System manages different tasks (processes) using various scheduling rules. This simulator helps you visualize the "brain" of a computer as it decides which task to run first.

---> What can this tool do?
Four Scheduling Rules: Test how tasks are handled using FCFS, Priority, Shortest Job First (SJF), and Round Robin.
Live View: Watch tasks move from a "Waiting Room" (Ready Queue) into the "CPU" to be processed.
Gantt Chart: Automatically draws a timeline of which task ran and for how long.
Results Table: Shows you the final "Wait Time" for every task once the simulation ends.

---> How to Use It?
1. Add Your Tasks
Click the "Add Manual" button several times.
Each click creates a new task (like P1, P2).
The tool automatically gives each task a Burst Time (BT) (how much work it needs) and a Priority (Pri).

2. Pick a Rule
Use the Algorithm dropdown menu at the top to choose a rule:
FCFS: "First Come, First Served" – the simplest way.
Priority: Tasks with the most important priority go first.
SJF: The shortest, quickest tasks are finished first.
Round Robin: Everyone gets a 2-second turn before going back to the end of the line.

3. Start & Reset
Click "Start Simulation" to watch the process in action.
Click "Reset" if you want to clear everything and start a brand new test.

4.  Definitions to Know:
BT (Burst Time): How many seconds the task needs to finish.
Pri (Priority): How important the task is (Lower numbers like 1 are higher priority).
Rem (Remaining Time): A countdown showing how many seconds are left for that specific task.

5. Setup Instructions
Download the MainDashboard.java and Process.java files.
Open them in your Java IDE (like IntelliJ or Eclipse).
Run the MainDashboard file to open the window.

6. Developed by: 
           Taiba Abid Jahangir
           Ayesha Farhan
           Duaa Nadeem

7. Academic Project: 
           Bahria University, Lahore