public final class SimulationResult {
    private final int producerCount;
    private final int consumerCount;
    private final int bufferSize;
    private final int producedRecords;
    private final long elapsedMillis;
    private final GlobalStatistics.Snapshot snapshot;

    public SimulationResult(
            int producerCount,
            int consumerCount,
            int bufferSize,
            int producedRecords,
            long elapsedMillis,
            GlobalStatistics.Snapshot snapshot
    ) {
        this.producerCount = producerCount;
        this.consumerCount = consumerCount;
        this.bufferSize = bufferSize;
        this.producedRecords = producedRecords;
        this.elapsedMillis = elapsedMillis;
        this.snapshot = snapshot;
    }

    public int getProducerCount() {
        return producerCount;
    }

    public int getConsumerCount() {
        return consumerCount;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getProducedRecords() {
        return producedRecords;
    }

    public long getElapsedMillis() {
        return elapsedMillis;
    }

    public GlobalStatistics.Snapshot getSnapshot() {
        return snapshot;
    }
}
