import java.util.ArrayList;
import java.util.List;

public final class SimulationRunner {
    public static final int TOTAL_RECORDS = 1000;

    public SimulationResult run(int producerCount, int consumerCount, int bufferSize) throws InterruptedException {
        ProductionController controller = new ProductionController(TOTAL_RECORDS);
        BoundedBuffer buffer = new BoundedBuffer(bufferSize);
        GlobalStatistics globalStatistics = new GlobalStatistics(producerCount);

        List<Thread> producerThreads = new ArrayList<>();
        List<Thread> consumerThreads = new ArrayList<>();

        for (int consumerId = 1; consumerId <= consumerCount; consumerId++) {
            Thread thread = new Thread(new Consumer(consumerId, buffer, globalStatistics), "Consumer-" + consumerId);
            consumerThreads.add(thread);
            thread.start();
        }

        long startNanos = System.nanoTime();

        for (int producerId = 1; producerId <= producerCount; producerId++) {
            Thread thread = new Thread(
                    new Producer(producerId, producerId, controller, buffer),
                    "Producer-" + producerId
            );
            producerThreads.add(thread);
            thread.start();
        }

        for (Thread producerThread : producerThreads) {
            producerThread.join();
        }

        for (int i = 0; i < consumerCount; i++) {
            buffer.put(SalesRecord.POISON_PILL);
        }

        for (Thread consumerThread : consumerThreads) {
            consumerThread.join();
        }

        long elapsedNanos = System.nanoTime() - startNanos;
        long elapsedMillis = elapsedNanos / 1_000_000L;

        return new SimulationResult(
                producerCount,
                consumerCount,
                bufferSize,
                controller.getProducedCount(),
                elapsedMillis,
                globalStatistics.snapshot()
        );
    }
}
