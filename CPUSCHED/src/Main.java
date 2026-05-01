import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Process> processes = readProcesses("input/datafile-txt.txt");

        System.out.println("Loaded " + processes.size() + " processes.");

        Scheduler.runFIFO(copyProcesses(processes));
        Scheduler.runSJF(copyProcesses(processes));
    }

    public static List<Process> readProcesses(String filename) {
        List<Process> processes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null && processes.size() < 500) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                // Skip header row
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split("\\s+");

                int arrivalTime = Integer.parseInt(parts[0]);
                int burstTime = Integer.parseInt(parts[1]);

                processes.add(new Process(arrivalTime, burstTime));
            }

        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: input file contains invalid numbers.");
        }

        return processes;
    }

    public static List<Process> copyProcesses(List<Process> original) {
        List<Process> copy = new ArrayList<>();

        for (Process p : original) {
            copy.add(new Process(p.arrivalTime, p.burstTime));
        }

        return copy;
    }
}