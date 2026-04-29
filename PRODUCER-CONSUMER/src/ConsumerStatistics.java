public final class ConsumerStatistics {
    private final int consumerId;
    private int consumedCount;
    private double aggregateSales;

    public ConsumerStatistics(int consumerId) {
        this.consumerId = consumerId;
        this.consumedCount = 0;
        this.aggregateSales = 0.0;
    }

    public void addRecord(SalesRecord record) {
        consumedCount++;
        aggregateSales += record.getSaleAmount();
    }

    public int getConsumerId() {
        return consumerId;
    }

    public int getConsumedCount() {
        return consumedCount;
    }

    public double getAggregateSales() {
        return aggregateSales;
    }
}
