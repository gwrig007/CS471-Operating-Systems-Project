import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Main {
    private static final int DEFAULT_BUFFER_SIZE = 20;

    private Main() {
    }

    public static void main(String[] args) {
        try {
            if (args.length >= 1 && "--batch".equals(args[0])) {
                int bufferSize = args.length >= 2 ? Integer.parseInt(args[1]) : DEFAULT_BUFFER_SIZE;
                String outputPath = args.length >= 3 ? args[2] : "output/summary-9-runs.csv";
                runBatchMode(bufferSize, outputPath);
                return;
            }

            if (args.length < 2 || args.length > 3) {
                printUsage();
                return;
            }

            int producerCount = Integer.parseInt(args[0]);
            int consumerCount = Integer.parseInt(args[1]);
            int bufferSize = args.length == 3 ? Integer.parseInt(args[2]) : DEFAULT_BUFFER_SIZE;

            SimulationRunner runner = new SimulationRunner();
            SimulationResult result = runner.run(producerCount, consumerCount, bufferSize);
            printResult(result);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            System.err.println("Simulation interrupted.");
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Arguments must be integers.");
            printUsage();
        } catch (IOException ioException) {
            System.err.println("Could not write batch output file: " + ioException.getMessage());
        }
    }

    private static void runBatchMode(int bufferSize, String outputPath) throws InterruptedException, IOException {
        int[] options = {2, 5, 10};
        SimulationRunner runner = new SimulationRunner();
        List<SimulationResult> results = new ArrayList<>();

        for (int producerCount : options) {
            for (int consumerCount : options) {
                System.out.println("\n=== Running configuration p=" + producerCount + ", c=" + consumerCount + " ===");
                SimulationResult result = runner.run(producerCount, consumerCount, bufferSize);
                results.add(result);
                printResult(result);
            }
        }

        writeBatchSummaryCsv(results, outputPath);
        System.out.println("\nBatch summary saved to: " + outputPath);
    }

    private static void writeBatchSummaryCsv(List<SimulationResult> results, String outputPath) throws IOException {
        Path path = Path.of(outputPath);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("producers,consumers,bufferSize,producedRecords,elapsedMillis,aggregateSales");
            writer.newLine();

            for (SimulationResult result : results) {
                writer.write(String.format(
                        Locale.US,
                        "%d,%d,%d,%d,%d,%.2f",
                        result.getProducerCount(),
                        result.getConsumerCount(),
                        result.getBufferSize(),
                        result.getProducedRecords(),
                        result.getElapsedMillis(),
                        result.getSnapshot().getAggregateSales()
                ));
                writer.newLine();
            }
        }
    }

    private static void printResult(SimulationResult result) {
        GlobalStatistics.Snapshot snapshot = result.getSnapshot();

        System.out.println("\n----- Simulation Summary -----");
        System.out.println("Producers: " + result.getProducerCount());
        System.out.println("Consumers: " + result.getConsumerCount());
        System.out.println("Buffer size: " + result.getBufferSize());
        System.out.println("Produced records: " + result.getProducedRecords());
        System.out.println("Total simulation time (ms): " + result.getElapsedMillis());

        System.out.println("\nStore-wide total sales:");
        double[] storeTotals = snapshot.getStoreTotals();
        for (int storeId = 1; storeId < storeTotals.length; storeId++) {
            System.out.println(String.format(Locale.US, "Store %d: %.2f", storeId, storeTotals[storeId]));
        }

        System.out.println("\nMonth-wise total sales (all stores):");
        double[] monthTotals = snapshot.getMonthTotals();
        for (int month = 1; month <= 12; month++) {
            System.out.println(String.format(Locale.US, "Month %02d: %.2f", month, monthTotals[month]));
        }

        System.out.println(String.format(Locale.US, "\nAggregate sales: %.2f", snapshot.getAggregateSales()));
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  Single run: java Main <producers> <consumers> [bufferSize]");
        System.out.println("  Batch run : java Main --batch [bufferSize] [outputCsvPath]");
    }
}
