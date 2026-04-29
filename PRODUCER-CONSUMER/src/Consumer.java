import java.util.Locale;

public final class Consumer implements Runnable {
    private final int consumerId;
    private final BoundedBuffer buffer;
    private final GlobalStatistics globalStatistics;
    private final ConsumerStatistics localStatistics;

    public Consumer(int consumerId, BoundedBuffer buffer, GlobalStatistics globalStatistics) {
        this.consumerId = consumerId;
        this.buffer = buffer;
        this.globalStatistics = globalStatistics;
        this.localStatistics = new ConsumerStatistics(consumerId);
    }

    @Override
    public void run() {
        try {
            while (true) {
                SalesRecord record = buffer.take();
                if (record.isPoisonPill()) {
                    break;
                }
                localStatistics.addRecord(record);
                globalStatistics.addRecord(record);
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            System.err.println("Consumer " + consumerId + " interrupted.");
        }

        System.out.println(String.format(
                Locale.US,
                "Consumer %d local stats: consumed=%d, aggregateSales=%.2f",
                localStatistics.getConsumerId(),
                localStatistics.getConsumedCount(),
                localStatistics.getAggregateSales()
        ));
    }
}
