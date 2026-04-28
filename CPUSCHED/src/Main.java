
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();

        String filename = "input/datafile-txt.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

           while ((line = br.readLine()) != null && processes.size() < 500) {
    line = line.trim();

    if (line.isEmpty()) continue;

    if (firstLine) {
        firstLine = false;
        continue;
    }

    String[] parts = line.split("\\s+");

    int arrival = Integer.parseInt(parts[0]);
    int burst = Integer.parseInt(parts[1]);

    processes.add(new Process(arrival, burst));
}

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        System.out.println("Loaded " + processes.size() + " processes.");

        Scheduler.runFIFO(processes);
    }
}