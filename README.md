# 🖥️ CPU Scheduling Simulator (Research Edition)

An interactive, real-time Java desktop application designed to visualize, analyze, and benchmark fundamental Operating System CPU scheduling algorithms. This project bridges the gap between theoretical OS concepts and practical algorithmic performance analysis.

Developed as a semester project for the **Operating Systems** course at **Bahria University, Lahore**.

---

## ✨ Features

* **Algorithmic Intelligence:** Supports four core scheduling paradigms:
  * **FCFS (First Come, First Served):** Standard non-preemptive FIFO queue processing.
  * **SJF (Shortest Job First):** Minimizes average waiting time by executing the shortest tasks first.
  * **Priority Scheduling:** Automatically prioritizes critical tasks based on assigned ranks.
  * **Round Robin (RR):** Simulates preemptive time-slicing with a fixed Time Quantum (2 seconds).
* **Research-Oriented Benchmarking:** Features a persistent **Master List** architecture. You can generate a set of processes once and test multiple algorithms sequentially against the *exact same queue* to scientifically determine the most optimal strategy.
* **Live Micro-Architecture View:** Watch tasks dynamically migrate from the **Ready Queue** (Waiting Room) directly into the **CPU** panel for live execution execution.
* **Dynamic Gantt Chart Timeline:** Instantly charts a colorful visual timeline mapping exactly which process occupied the CPU and for how long.
* **Comprehensive Metrics Engine:** Automatically computes a results matrix detailing **Arrival Time (AT)**, **Burst Time (BT)**, **Completion Time (CT)**, **Turnaround Time (TAT)**, and **Waiting Time (WT)** alongside overall system averages.

---

## 📊 Key Performance Formulas Implemented

The metrics engine evaluates performance at the end of each simulation using standard OS formulas:

* **Turnaround Time ($TAT$):** The total lifecycle duration of a process in the system.
  $$TAT = Completion\ Time - Arrival\ Time$$
* **Waiting Time ($WT$):** The total time spent sitting idle in the Ready Queue.
  $$WT = Turnaround\ Time - Burst\ Time$$
* **System Averages:** Evaluates global algorithm efficiency.
  $$\text{Average Wait Time} = \frac{\sum WT}{\text{Total Processes}}$$

---

## 🚀 How to Use the Simulator

1. **Populate the Queue:** Click the **Add Manual** button to generate custom processes (e.g., P1, P2). Each process is assigned randomized workloads and priority metrics.
2. **Select Your Evaluation Rule:** Choose your target scheduling strategy from the **Algorithm** dropdown menu.
3. **Execute:** Hit **Start** to watch the scheduler take control. Observe the dynamic countdown of **Rem** (Remaining Time) inside the CPU stage.
4. **Benchmark & Compare:** Review the final analytics matrix popup. Change the algorithm and hit **Start** again to evaluate the *same workload* under different rules, or hit **Reset** to clear the system state entirely.

---

## 🛠️ Project Setup Instructions

### Prerequisites
* Java Development Kit (JDK 8 or higher)
* A Java IDE (IntelliJ IDEA, Eclipse, or NetBeans)

### Execution Steps
1. Clone or download the source code files: `MainDashboard.java` and `Process.java`.
2. Ensure both files reside in the correct directory package path: `src/com/simulator/`.
3. Open the project in your preferred IDE.
4. Compile and run `MainDashboard.java` to launch the graphic simulator desktop frame.

```text
├── src/
│   └── com/
│       └── simulator/
│           ├── MainDashboard.java   # Graphical UI Engine & Timer Loops
│           └── Process.java         # Process Entity Data Model
└── README.md                        # Project Documentation
