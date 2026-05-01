
import java.util.*;

public class Scheduler {

    public static void runFIFO(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }

            p.startTime = currentTime;
            p.finishTime = currentTime + p.burstTime;

            currentTime = p.finishTime;
        }

        printStats(processes);
    }

    public static void printStats(List<Process> processes) {
        int totalWaiting = 0;
        int totalTurnaround = 0;
        int totalResponse = 0;
        int totalBurst = 0;

        for (Process p : processes) {
            int waiting = p.startTime - p.arrivalTime;
            int turnaround = p.finishTime - p.arrivalTime;
            int response = waiting;

            totalWaiting += waiting;
            totalTurnaround += turnaround;
            totalResponse += response;
            totalBurst += p.burstTime;
        }

        int n = processes.size();
        int totalTime = processes.get(n - 1).finishTime;

        System.out.println("Processes: " + n);
        System.out.println("Total Time: " + totalTime);
        System.out.println("CPU Utilization: " + ((double) totalBurst / totalTime));
        System.out.println("Avg Waiting Time: " + (double) totalWaiting / n);
        System.out.println("Avg Turnaround Time: " + (double) totalTurnaround / n);
        System.out.println("Avg Response Time: " + (double) totalResponse / n);
    }

    public static void runSJF(List<Process> processes) {
    List<Process> completed = new ArrayList<>();
    List<Process> ready = new ArrayList<>();

    processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

    int currentTime = 0;
    int index = 0;

    while (completed.size() < processes.size()) {

        // Add arrived processes to ready queue
        while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
            ready.add(processes.get(index));
            index++;
        }

        if (ready.isEmpty()) {
            currentTime++;
            continue;
        }

        // Pick shortest job
        ready.sort(Comparator.comparingInt(p -> p.burstTime));
        Process p = ready.remove(0);

        p.startTime = currentTime;
        p.finishTime = currentTime + p.burstTime;

        currentTime = p.finishTime;

        completed.add(p);
    }

    System.out.println("\n--- SJF RESULTS ---");
    printStats(processes);
}
}