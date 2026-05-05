# CPU Scheduling Simulation (CS471 Project)

This folder contains the implementation for Problem 1: CPU Scheduling.

Language: Java

--------------------------------------------------

Overview

This program simulates a CPU scheduler using:

- 500 processes
- Each process defined by:
  - Arrival Time
  - CPU Burst Time

Two scheduling algorithms are implemented:

- FIFO (First-In First-Out)
- SJF (Shortest Job First, non-preemptive)

--------------------------------------------------

Directory Layout

CPUSCHED/
  src/      Java source code
  input/    input data file (datafile-txt.txt)
  output/   sample output file(s)
  README.md

--------------------------------------------------

Input Format

The program reads from:

input/datafile-txt.txt

Each line contains:

ArrivalTime   CPUBurstLength

Only the first 500 processes are used in each run.

--------------------------------------------------

Statistics Reported

For each scheduling algorithm, the program calculates:

- Number of processes
- Total elapsed time
- Throughput
- CPU utilization
- Average waiting time
- Average turnaround time
- Average response time

--------------------------------------------------

Compile and Run

Open terminal in the CPUSCHED directory and run:

cd src
javac *.java
cd ..
java -cp src Main

--------------------------------------------------

Output

The program prints results for both algorithms:

--- FIFO RESULTS ---
...

--- SJF RESULTS ---
...

To save output to a file:

java -cp src Main > output/output.txt

--------------------------------------------------

Algorithm Details

FIFO:
Processes are executed in the order they arrive.

SJF (Non-Preemptive):
At each scheduling decision, the process with the shortest CPU burst time is selected from the ready queue. Once a process starts, it runs to completion.

--------------------------------------------------

Notes

- Simulation time is measured in CPU burst units (not real time).
- Response time is equal to waiting time for non-preemptive algorithms.
- Throughput is calculated as:

  total burst time / number of processes

--------------------------------------------------

Sample Output

A sample output file is included:

output/output.txt

--------------------------------------------------

Author

Grace Wright
Old Dominion University