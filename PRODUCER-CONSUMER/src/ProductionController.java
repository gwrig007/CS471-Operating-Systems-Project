public final class ProductionController {
    private final int maxRecords;
    private int producedCount;

    public ProductionController(int maxRecords) {
        this.maxRecords = maxRecords;
        this.producedCount = 0;
    }

    public synchronized boolean reserveNextRecord() {
        if (producedCount >= maxRecords) {
            return false;
        }
        producedCount++;
        return true;
    }

    public synchronized int getProducedCount() {
        return producedCount;
    }
}
